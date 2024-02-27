# Description: Makefile for Lyra project
# Author: Yuniel Acosta <https://twitter.com/yacosta738>

GRADLE = ./gradlew

# Colors
RED = \033[0;31m
GREEN = \033[0;32m
YELLOW = \033[0;33m
NC = \033[0m

# Targets and Dependencies
.PHONY: all clean build test

# Default target
all: build

# Build target
build:
	@echo "${YELLOW}Building the project with Gradle...${NC}"
	$(GRADLE) build
	@echo "${GREEN}Build completed successfully.${NC}"

# Clean target
clean:
	@echo "${YELLOW}Cleaning the project with Gradle...${NC}"
	$(GRADLE) clean
	@echo "${GREEN}Clean completed successfully.${NC}"

# Test target
test:
	@echo "${YELLOW}Running tests with Gradle...${NC}"
	$(GRADLE) test
	@echo "${GREEN}Tests completed successfully.${NC}"

# Help target
help:
	@echo "${YELLOW}Usage: make [target]${NC}"
	@echo "${YELLOW}Targets:${NC}"
	@echo "  ${GREEN}all      :${NC} Build the project"
	@echo "  ${GREEN}build    :${NC} Build the project"
	@echo "  ${GREEN}clean    :${NC} Clean the project"
	@echo "  ${GREEN}test     :${NC} Run tests"
	@echo "  ${GREEN}help     :${NC} Show this help message"

# If no target is specified, show help
.DEFAULT_GOAL := help
