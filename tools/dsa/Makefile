# Use V=1 to see full verbosity
QUIET_  = @
QUIET   = $(QUIET_$(V))

SOURCE = dsa.sh
TARGET = dsa

all: $(TARGET)

$(TARGET): $(SOURCE)
	@echo "  INSTALL $@"
	$(QUIET)install --mode=755 $< $@

clean distclean:
	$(QUIET)rm -fv $(TARGET)
