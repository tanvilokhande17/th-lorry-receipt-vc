const BASE_URL = "https://9bppx3gm47.execute-api.ap-southeast-1.amazonaws.com";
const ENV = "/dev";

export const LOGIN_URL = `${BASE_URL}${ENV}/user/signin`;
export const SIGNUP_URL = `${BASE_URL}${ENV}/user/signup`;
export const GET_LORRY_RECEIPT_URL = `${BASE_URL}${ENV}/lorryreceipt?role=`;
