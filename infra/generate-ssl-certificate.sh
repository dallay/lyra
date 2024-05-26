#!/bin/bash

# Variables
BASE_FOLDER="${1:-$(pwd)}"  # Use the first argument or the current directory if not provided
SSL_FOLDER="$BASE_FOLDER/ssl"

# Prompt for password if not set
if [ -z "$SSL_KEYSTORE_PASSWORD" ]; then
  read -sp "Enter keystore password (default 'changeme'): " PASSWORD_INPUT
  echo
  PASSWORD="${PASSWORD_INPUT:-changeme}"
else
  PASSWORD="$SSL_KEYSTORE_PASSWORD"
fi

echo "🔐 Generating SSL certificate and key for the application"

# Create the SSL directory if it does not exist
mkdir -p "$SSL_FOLDER"

# Install mkcert and generate the key and certificate files
mkcert -install
mkcert -key-file "$SSL_FOLDER/key.pem" -cert-file "$SSL_FOLDER/cert.pem" example.com "*.example.com" example.test localhost 127.0.0.1 ::1

# Generate the PKCS12 file (keystore.p12) for the Java application
openssl pkcs12 -export -in "$SSL_FOLDER/cert.pem" -inkey "$SSL_FOLDER/key.pem" -out "$SSL_FOLDER/keystore.p12" -name "server" -password pass:"$PASSWORD"

# (Optional) Convert PKCS12 to JKS if needed for the Java application
keytool -importkeystore \
  -deststorepass "$PASSWORD" -destkeypass "$PASSWORD" -destkeystore "$SSL_FOLDER/keystore.jks" \
  -srckeystore "$SSL_FOLDER/keystore.p12" -srcstoretype PKCS12 -srcstorepass "$PASSWORD" \
  -alias "server" -noprompt

echo "🟢 PKCS12 file created and moved to $SSL_FOLDER"
echo "🟢 JKS file created and moved to $SSL_FOLDER (if needed)"
echo "🟢 Certificate and key saved as $SSL_FOLDER/cert.pem and $SSL_FOLDER/key.pem"
echo "🔑 Keystore password: $PASSWORD"
