FROM node

WORKDIR /front

COPY /package*.json ./

COPY /src ./src

COPY /public ./public

RUN npm install -g sass

RUN npm install

EXPOSE 3000

CMD ["/bin/bash", "-c", "npm run build;npm install -g serve;serve -s build"]