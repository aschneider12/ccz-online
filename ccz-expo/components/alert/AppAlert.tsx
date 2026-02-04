// alert/AppAlert.tsx
import React from 'react';
import { Modal, Platform, Pressable, StyleSheet, Text, View } from 'react-native';

import { AlertType } from './AlertService';

const COLORS: Record<AlertType, string> = {
  info: '#007AFF',     // azul
  success: '#2ECC71',  // verde
  alert: '#F1C40F',    // amarelo
  error: '#E74C3C',    // vermelho
};

type Props = {
  visible: boolean;
  title?: string;
  message: string;
  type: AlertType;
  onClose: () => void;
};

export function AppAlert({ visible, title, message, type, onClose }: Props) {

  const color = COLORS[type];

  return (
    <Modal transparent visible={visible} animationType="fade">
      <View style={styles.overlay}>
        <View style={styles.box}>
          {title && (
            <Text style={[styles.title, { color }]}>
              {title}
            </Text>
          )}
         
          <Text style={styles.message}>{message}</Text>

          <Pressable
            style={[styles.button, { backgroundColor: color }]}
            onPress={onClose}
          >
            <Text style={styles.buttonText}>OK</Text>
          </Pressable>
        </View>
      </View>
    </Modal>
  );
}

const styles = StyleSheet.create({
  overlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.45)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  box: {
    width: '85%',
    maxWidth: 420,
    backgroundColor: '#fff',
    padding: 20,
    borderRadius: 12,
    alignItems: 'center',
    elevation: 6,
    ...(Platform.OS === 'web'
      ? { boxShadow: '0 6px 20px rgba(0,0,0,0.25)' }
      : {}),
  },
  title: {
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 6,
  },
  message: {
    fontSize: 15,
    textAlign: 'center',
    marginBottom: 18,
  },
  button: {
    backgroundColor: '#007AFF',
    paddingHorizontal: 24,
    paddingVertical: 10,
    borderRadius: 8,
  },
  buttonText: {
    color: '#fff',
    fontWeight: '600',
  },
});
