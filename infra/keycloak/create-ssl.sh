#!/bin/bash

# Get the directory of the script
DIR="$(dirname "$0")"

# Create the ssl directory if it doesn't exist
mkdir -p "$DIR/ssl"

# Generate a new self-signed SSL certificate and key
openssl req -newkey rsa:2048 -nodes -keyout "$DIR/ssl/privkey.pem" -x509 -days 3650 -out "$DIR/ssl/fullchain.pem" -subj "/C=ES/ST=Madrid/L=Madrid/O=HomeLab/CN=localhost"

# Set the permissions of the key file
sudo chmod 655 $DIR/ssl/*
