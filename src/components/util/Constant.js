const BASE_URL = "https://9bppx3gm47.execute-api.ap-southeast-1.amazonaws.com";
const ENV = "/dev";

export const LOGIN_URL = `${BASE_URL}${ENV}/user/signin`;
export const SIGNUP_URL = `${BASE_URL}${ENV}/user/signup`;
export const LORRY_RECEIPT_URL = `${BASE_URL}${ENV}/lorryreceipt`;
export const UPDATE_STATUS_URL = `${BASE_URL}${ENV}/lorryreceipt/action`;

export const WALLET_CREDENTIALS_URL =
  "https://cloud-wallet-api.prod.affinity-project.org/api/v1/wallet/credentials/";
