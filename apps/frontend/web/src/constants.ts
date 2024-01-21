export const API_URL = `${import.meta.env.SITE}${import.meta.env.BASE_URL}api/`;
export const BACKEND_API_URL = import.meta.env.DEV ? API_URL.replace('api/', 'backend/api/') : API_URL;
