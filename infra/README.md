# SSL Certificate and Keystore Generation Script

This repository contains a script to generate SSL certificates and keystores using `mkcert`, and configure them for a Spring Boot application. The script will generate PEM files (`key.pem` and `cert.pem`), a PKCS12 keystore (`keystore.p12`), and optionally a Java KeyStore (JKS) (`keystore.jks`).

## Prerequisites

Ensure the following tools are installed on your system:
- [mkcert](https://github.com/FiloSottile/mkcert)
- [openssl](https://www.openssl.org/)
- `keytool` (comes with JDK)

## Script Usage

The script will:
1. Create an SSL directory.
2. Generate a private key and certificate using `mkcert`.
3. Convert the generated PEM files into a PKCS12 keystore.
4. Optionally convert the PKCS12 keystore to a JKS keystore.
5. Prompt for a keystore password, use an environment variable if set, or use a default password.

### Running the Script

1. **Clone the repository** (if applicable):

    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Make the script executable**:

    ```sh
    chmod +x generate-ssl-certificate.sh
    ```

3. **Run the script**:

    ```sh
    ./generate-ssl-certificate.sh
    ```

   You can specify a custom base directory as the first argument:

    ```sh
    ./generate-ssl-certificate.sh /path/to/directory
    ```

   The script will prompt you to enter a keystore password. If you don't provide one, it will use the password from the environment variable `SSL_KEYSTORE_PASSWORD` if set, or default to `changeme`.

### Environment Variable

You can set the keystore password via an environment variable `SSL_KEYSTORE_PASSWORD`:

```sh
export SSL_KEYSTORE_PASSWORD=mysecurepassword
```

Or in Windows:

```shell
set SSL_KEYSTORE_PASSWORD=mysecurepassword
```
### Generated Files
The script will generate the following files in the ssl directory:

- `key.pem`: Private key
- `cert.pem`: Certificate
- `keystore.p12`: PKCS12 keystore
- `keystore.jks`: Java KeyStore (optional)
