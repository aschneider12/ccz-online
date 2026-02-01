import { API_URLS } from '@/config/api';
import { useUser } from '@/context/context';
import { IAlertaCidadao } from '@/interfaces/IAlertaCidadao';
import React, { useEffect, useState } from 'react';
import {
  ActivityIndicator,
  FlatList,
  StyleSheet,
  Text,
  View,
} from 'react-native';


export default function SolicitacoesScreen() {

  const { user } = useUser(); 

  const [solicitacoes, setSolicitacoes] = useState<IAlertaCidadao[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    buscarSolicitacoes();
  }, []);

  async function buscarSolicitacoes() {
    try {
      console.log('buscando solicitacoes')
      const response = await fetch(API_URLS.ALERTA_CIDADAO.BASE+"?usuarioId="+user?.id);
      const data = await response.json();
      setSolicitacoes(data);

    } catch (error) {
      console.error('Erro ao buscar solicitações', error);
    } finally {
      setLoading(false);
    }
  }

  function renderItem({ item }: { item: IAlertaCidadao }) {
    return (
      <View style={styles.card}>
        <Text style={styles.titulo}>{item.descricao}</Text>
        <Text style={styles.tipo}>{item.tipoNotificacaoId}</Text>
        <Text style={styles.data}>{item.data}</Text>
      </View>
    );
  }

  if (loading) {
    return (
      <View style={styles.loading}>
        <ActivityIndicator size="large" color="#007AFF" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={solicitacoes}
        keyExtractor={(_, index) => index.toString()}
        renderItem={renderItem}
        contentContainerStyle={{ paddingBottom: 16 }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F5F5F5',
    padding: 16,
  },
  card: {
    backgroundColor: '#FFF',
    padding: 16,
    borderRadius: 8,
    marginBottom: 12,
    elevation: 2,
  },
  titulo: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  tipo: {
    fontSize: 14,
    color: '#555',
  },
  data: {
    fontSize: 12,
    color: '#888',
    marginTop: 4,
  },
  loading: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
