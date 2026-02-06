import Constants from "expo-constants";

const API_BASE_URL =
  Constants.expoConfig?.extra?.API_BASE_URL ??
  "http://localhost:8080/api/v1";

export const API_URLS = {
  BASE: API_BASE_URL,
  AUTH: {
    LOGIN: `${API_BASE_URL}/auth/login`,
    REGISTER: `${API_BASE_URL}/auth/register`,
  },
  USUARIOS: {
    BASE: `${API_BASE_URL}/usuarios`,
    SOLICITACOES_PROXIMAS: `${API_BASE_URL}/usuarios/solicitacoes-proximas`,
  },
  ESPECIES: {
    BASE: `${API_BASE_URL}/especies`,
  },
  TIPOS_NOTIFICACAO: {
    BASE: `${API_BASE_URL}/tipos-notificacao`,
  },
  MUNICIPIOS: {
    BASE: `${API_BASE_URL}/municipios`,
  },
  ALERTA_CIDADAO: {
    BASE: `${API_BASE_URL}/alertas-cidadao`,
  },
   ALERTTA_CCZ: {
    BASE: `${API_BASE_URL}/alertas-ccz`,
  },

};
