FROM node

WORKDIR /front

COPY /package*.json ./

COPY /src ./src

COPY /public ./public

RUN npm install

EXPOSE 3000

CMD ["npm", "start"]