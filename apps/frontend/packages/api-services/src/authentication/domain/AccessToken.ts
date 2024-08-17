
/**
 * Class representing an access token.
 */
export default class AccessToken {
    /**
     * Create an access token.
     * @param {string} token - The token string.
     * @param {number} expiresIn - The time in seconds until the token expires.
     * @param {string} refreshToken - The refresh token string.
     * @param {number} refreshExpiresIn - The time in seconds until the refresh token expires.
     * @param {string} tokenType - The type of the token.
     * @param {number|null} [notBeforePolicy=null] - The not-before policy timestamp, or null if not applicable.
     * @param {string|null} [sessionState=null] - The session state, or null if not applicable.
     * @param {string|null} [scope=null] - The scope of the token, or null if not applicable.
     */
    constructor(
        readonly token: string,
        readonly expiresIn: number,
        readonly refreshToken: string,
        readonly refreshExpiresIn: number,
        readonly tokenType: string,
        readonly notBeforePolicy: number | null = null,
        readonly sessionState: string | null = null,
        readonly scope: string | null = null
    ) {}
}
