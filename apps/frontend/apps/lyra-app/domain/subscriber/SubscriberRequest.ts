/**
 * Subscriber request
 * @interface SubscriberRequest
 * @property {string} email - Subscriber email
 * @property {string} firstname - Subscriber firstname
 * @property {string} [lastname] - Subscriber lastname
 */
export interface SubscriberRequest {
  email: string;
  firstname?: string;
  lastname?: string;
}
