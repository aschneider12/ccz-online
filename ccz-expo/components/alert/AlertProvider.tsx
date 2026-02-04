// alert/AlertProvider.tsx
import React, { useEffect, useState } from 'react';
import { AlertType, registerAlert } from './AlertService';
import { AppAlert } from './AppAlert';

type AlertState = {
  title?: string;
  message: string;
  type: AlertType;
  visible: boolean;
};

export function AlertProvider({ children }: { children: React.ReactNode }) {
  const [alert, setAlert] = useState<AlertState>({
    visible: false,
    message: '',
    type: 'info',
  });

  useEffect(() => {
    registerAlert(({ title, message, type = 'info'}) => {
      setAlert({ title, message, type, visible: true });
    });
  }, []);

  return (
    <>
      {children}
      <AppAlert
        visible={alert.visible}
        title={alert.title}
        message={alert.message}
        type={alert.type}
        onClose={() =>
          setAlert(prev => ({ ...prev, visible: false }))
        }
      />
    </>
  );
}
