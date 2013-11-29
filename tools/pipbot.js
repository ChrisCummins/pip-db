#!/usr/bin/env node


function fatal(msg) {
  console.log(msg);
  process.exit(1);
}


function badCommand() {
  fatal("I don't understand!");
}


function help() {
  console.log("                  ,--.    ,--.");
  console.log("                 ((O ))--((O ))");
  console.log("               ,'_`--'____`--'_`.");
  console.log("              _:  ____________  :_");
  console.log("             | | ||::::::::::|| | |");
  console.log("             | | ||::::::::::|| | |");
  console.log("             | | ||::::::::::|| | |");
  console.log("             |_| |/__________\\| |_|");
  console.log("               |________________|");
  console.log("            __..-'            `-..__");
  console.log("         .-| : .----------------. : |-.");
  console.log("       ,\\ || | |\\______________/| | || /.");
  console.log("      /`.\\:| | ||  __  __  __  || | |;/,'\\");
  console.log("     :`-._\\;.| || '--''--''--' || |,:/_.-':");
  console.log("     |    :  | || .----------. || |  :    |");
  console.log("     |    |  | || '--pipbot--' || |  |    |");
  console.log("     |    |  | ||   _   _   _  || |  |    |");
  console.log("     :,--.;  | ||  (_) (_) (_) || |  :,--.;");
  console.log("     (`-'|)  | ||______________|| |  (|`-')");
  console.log("      `--'   | |/______________\\| |   `--'");
  console.log("             |____________________|");
  console.log("              `.________________,'");
  console.log("               (_______)(_______)");
  console.log("               (_______)(_______)");
  console.log("               (_______)(_______)");
  console.log("               (_______)(_______)");
  console.log("              |        ||        |");
  console.log("              '--------''--------'");
  console.log("");
  console.log("Hello there. My name is pipbot. These are some of the things " +
              "I can do:");
  console.log("");
  console.log("    pipbot list me <type>");
  console.log("    pipbot build me <build>");
  console.log("");
}


function list(scripts, argv) {
  var target = argv[0] || 'builds';
  argv.shift();

  switch (target) {

  case 'builds':
    scripts.forEach(function(script) {
      var s = script.name;

      if (script.description !== undefined &&
          script.description !== '') {
        s += ' - ' + script.description;
      }

      console.log(s);
    });
    break;

  default:
    badCommand();
    break;
  }
}


function build(scripts, argv) {


  function getScriptByName(scripts, name) {

    for (var i = 0; i < scripts.length; i++) {
      if (scripts[i].name == name)
        return scripts[i];
    }

    console.log("I don't know how to build '" + name + "'");
    process.exit(1);
  }


  function args(script, command) {

    var args = script.args[command];

    if (args !== undefined) {
      var s = '';

      for (var i = 0; i < args.length; i++)
        s += args[i] + ' ';

      return s;
    } else {
      return '';
    }
  }


  function autogen(script) {
    console.log("$ " + "./autogen.sh " + args(script, 'autogen'));

    var autogen = spawn("./autogen.sh", script.args['autogen']);

    autogen.stdout.on('data', function(data) {
      process.stdout.write('' + data);
    });

    autogen.stderr.on('data', function(data) {
      process.stdout.write('' + data);
    });

    autogen.on('close', function(code) {
      if (code != 0)
        process.exit(code);

      configure(script);
    });

    autogen.on('error', function(err) {
      console.log('Failed to run autogen.sh!');
      process.exit(3);
    });
  }


  function configure(script) {
    console.log("$ " + "./configure " + args(script, 'configure'));

    var configure = spawn("./configure", script.args['configure']);

    configure.stdout.on('data', function(data) {
      process.stdout.write('' + data);
    });

    configure.stderr.on('data', function(data) {
      process.stdout.write('' + data);
    });

    configure.on('close', function(code) {
      if (code != 0)
        process.exit(code);

      make(script);
    });

    configure.on('error', function(err) {
      console.log('Failed to run configure!');
      process.exit(3);
    });
  }


  function make(script) {
    console.log("$ " + "make " + args(script, 'make'));

    var make = spawn("make", script.args['make']);

    make.stdout.on('data', function(data) {
      process.stdout.write('' + data);
    });

    make.stderr.on('data', function(data) {
      process.stdout.write('' + data);
    });

    make.on('close', function(code) {
      if (code != 0)
        process.exit(code);
    });

    make.on('error', function(err) {
      console.log('Failed to run make!');
      process.exit(3);
    });
  }

  var target = argv[0];

  if (target === undefined || target == '') {
    console.log("Tell me what to build!\n");
    list(scripts, ['builds']);
    process.exit(0);
  }

  var script = getScriptByName(scripts, target);

  autogen(script);
}


function version() {
  exec("grep 'm4_define(\\s*\\[pipdb_major_version\\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\\1/'",
       function(err, stdout, stderr) {
         process.stdout.write(parseInt(stdout) + '.');

         exec("grep 'm4_define(\\s*\\[pipdb_minor_version\\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\\1/'",
              function(err, stdout, stderr) {
                process.stdout.write(parseInt(stdout) + '.');

                exec("grep 'm4_define(\\s*\\[pipdb_micro_version\\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\\1/'",
                     function(err, stdout, stderr) {
                       console.log(parseInt(stdout));
                     });
              });
       });
}


var fs = require('fs');
var exec = require('child_process').exec;
var spawn = require('child_process').spawn;

var projectdir = '/home/chris/src/pip-db';
var scriptsdir = projectdir + '/scripts/pipbot';


/* Build our list of scripts */
var scripts = [];

fs.readdirSync(scriptsdir).forEach(function(f) {
  if (f.match(/\.json$/)) {
    try {
      scripts.push(require(scriptsdir + "/" + f));
    } catch (e) {
      fatal("Syntax error in script '" + scriptsdir + "/" + f + '"');
    }
  }
});


/* Get user command */
var cmd = '';
var argv = process.argv;

argv.shift();
argv.shift();

if (argv[0] !== undefined) {
  cmd += argv[0];
  argv.shift();
}
if (argv[0] !== undefined) {
  cmd += ' ' + argv[0];
  argv.shift();
}


/* Process user command */
switch (cmd) {

case 'list me':
  list(scripts, argv);
  break;

case 'build me':
  build(scripts, argv);
  break;

case 'version me':
  version();
  break;

case '':
case 'help':
  help();
  break;

default:
  badCommand();
}
