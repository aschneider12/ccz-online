import { useUser } from '@/context/context';
import { Ionicons } from '@expo/vector-icons';
import { useRouter } from 'expo-router';
import { useState } from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';

export function HeaderMenu() {
  const [open, setOpen] = useState(false);
  const router = useRouter();
  const contextUser = useUser();

  const handleLogout = () => {
    setOpen(false);
    // limpar auth aqui
    contextUser.setUser(null);
    router.replace('/');
  };

  return (
    <View style={{ marginRight: 12 }}>
      <Pressable onPress={() => setOpen(!open)}>
        <Ionicons name="ellipsis-vertical" size={22} />
      </Pressable>

      {open && (
        <View style={styles.menu}>
          <Pressable
            style={styles.item}
            onPress={() => {
              setOpen(false);
              router.push('/meuPerfil');
            }}
          >
            <Ionicons name="person-outline" size={18} />
            <Text style={styles.text}>Perfil</Text>
          </Pressable>

          <Pressable style={styles.item} onPress={handleLogout}>
            <Ionicons name="log-out-outline" size={18} color="red" />
            <Text style={[styles.text, { color: 'red' }]}>Sair</Text>
          </Pressable>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  menu: {
    position: 'absolute',
    top: 28,
    right: 0,
    backgroundColor: '#fff',
    borderRadius: 8,
    elevation: 4,
    shadowColor: '#000',
    shadowOpacity: 0.15,
    shadowRadius: 8,
    paddingVertical: 4,
    width: 160,
    zIndex: 999,
  },
  item: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 12,
  },
  text: {
    marginLeft: 8,
    fontSize: 14,
  },
});
