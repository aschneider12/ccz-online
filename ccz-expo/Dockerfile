# ---------- Stage 1: build do Expo Web ----------
FROM node:20-alpine AS build

WORKDIR /app

# Copia arquivos de dependências (cache eficiente)
COPY package.json package-lock.json* yarn.lock* ./

RUN npm install

# Copia o restante do projeto
COPY . .

# Gera o build web do Expo
RUN npx expo export --platform web


# ---------- Stage 2: servidor web ----------
FROM nginx:alpine

# Remove conteúdo padrão do nginx
RUN rm -rf /usr/share/nginx/html/*

# Copia o build gerado pelo Expo
# Se sua pasta for "web-build", troque "dist" abaixo
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]