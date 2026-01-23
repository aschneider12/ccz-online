
export interface IAlertaCidadao {

    descricao: string;
    endereco: string;
    municipioId: string | number;
    tipoNotificacaoId: string | number; 
    especieId: string | number;
    usuarioId: number;
    coordLatitude: string | number;
    coordLongitude: string | number;
    data: Date;

}