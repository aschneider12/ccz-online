// alert/AlertService.ts
export type AlertType = 'info' | 'success' | 'alert' | 'error';

type AlertPayload = {
  title?: string;
  message: string;
  type?: AlertType;
};

let showAlertFn: ((data: AlertPayload) => void) | null = null;

export function registerAlert(fn: (data: AlertPayload) => void) {
  showAlertFn = fn;
}

export function showAlert(title: string, message: string, type: AlertType = 'info') {
  if (!showAlertFn) {
    console.warn('AlertProvider não registrado');
    return;
  }

  showAlertFn({ message, title, type});
}
