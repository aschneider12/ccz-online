import SharedButton from "@/components/SharedButton";
import { useRouter } from "expo-router";
import React, { useEffect } from "react";
import { StyleSheet, Text, View } from "react-native";
import { useUser } from "../../context/context";

export default function Home() {
  
    const router = useRouter();
 
    const { user } = useUser();

    useEffect(() => {
      
      if (!user) router.replace("/");
      
    }, [user]);

    if (user === null) {
      return null; // ou um loading
    }
  //const token = authContext.currentUser?.getIdTokenResult();
  //token?.then( (authObject) => {
   // console.log(authObject.token)
 // })
  
  return (

    <View style={styles.container}>

      <Text style={styles.text}>Bem vindo(a) {user?.nome}</Text>
      <Text style={styles.text}>Perfil de acesso: {user?.perfil}</Text>

      {user?.perfil === 'CIDADAO' ? (
            <>
               <SharedButton
                  title="Solicitar auxílio"
                  onPress={() => router.push("/cadastroSolicitacao")}
                  style={{ backgroundColor: '#007AFF' }}
                  textStyle={{ fontSize: 20 }}
                />

                <SharedButton
                  title="Minhas solicitações"
                  onPress={() => router.push("/minhasSolicitacoes")}
                  style={{ backgroundColor: '#E74C3C' }}
                  textStyle={{ fontSize: 20 }}
                />
            </>
          ) : (
          <>
              <SharedButton
                title="Cadastrar alerta"
                onPress={() => router.push("/cadastrarAlerta")}
                style={{ backgroundColor: '#007AFF' }}
                textStyle={{ fontSize: 20 }}
              />

              <SharedButton
                title="Alertas emitidos"
                onPress={() => router.push("/meusAlertas")}
                style={{ backgroundColor: '#E74C3C' }}
                textStyle={{ fontSize: 20 }}
              />
            </>
          )}
  </View>
)};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    fontSize: 22,
    fontWeight: "bold",
  },
});
