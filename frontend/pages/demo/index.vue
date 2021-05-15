<template>
  <v-row align="center" justify="space-between">
    <v-col cols="12">
      <h3>Demo add a user</h3>
      <v-row justify="center">
        <v-col md="8" lg="6" justify="start">
          <v-row align="start">
            <v-col cols="7">
              <v-text-field
                v-model="currentUser.firstName"
                outlined
                rounded
                label="First name"
                prepend-icon="mdi-account"
              ></v-text-field>
            </v-col>
            <v-col cols="5">
              <v-text-field
                v-model="currentUser.lastName"
                outlined
                rounded
                label="Last name"
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                v-model="currentUser.email"
                outlined
                rounded
                prepend-icon="mdi-email"
                label="Email"
              ></v-text-field>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row justify="center">
        <v-btn class="mx-3" color="primary" @click="addUser">
          Add this user
        </v-btn>
        <v-btn class="mx-3 darken-1" @click="generateUsers(10)">
          Generate 10 random user
        </v-btn>
        <v-btn
          class="mx-3 darken-1"
          color="red"
          style="color: white"
          @click="deleteAllUser"
        >
          Delete All
        </v-btn>
      </v-row>
    </v-col>
    <v-col cols="12">
      <v-row justify="center">
        <v-col cols="12" lg="8" md="10">
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">Avatar</th>
                  <th class="text-left">First name</th>
                  <th class="text-left">Last name</th>
                  <th class="text-left">Email</th>
                  <th class="text-left">Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in users" :key="item.name">
                  <td>
                    <v-avatar color="primary" size="30" style="color: white">
                      {{ item.firstName[0] + item.lastName[0] }}
                    </v-avatar>
                  </td>
                  <td>{{ item.firstName }}</td>
                  <td>{{ item.lastName }}</td>
                  <td>{{ item.email }}</td>
                  <td>
                    <v-btn
                      x-small
                      color="red"
                      style="color: white"
                      outlined
                      fab
                      @click="removeUser(item)"
                    >
                      <v-icon>mdi-close</v-icon>
                    </v-btn>
                  </td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
      </v-row>
    </v-col>

    <v-col cols="12">
      <v-divider class="mx-12 my-3"></v-divider>
    </v-col>
    <v-col cols="12">
      <h3>Playground</h3>
      <v-row justify="center">
        <v-col sm="12" md="8" lg="6" align="start" justify="start">
          <v-row align="start">
            <v-col cols="12" sm="12">
              <v-text-field
                rounded
                outlined
                label="Log message"
                v-model="log.message"
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="12">
              <v-select
                rounded
                outlined
                :items="levels"
                v-model="log.level"
                label="Log level"
              ></v-select>
            </v-col>
            <v-col cols="5" sm="5">
              <v-text-field
                rounded
                outlined
                label="Business Property"
                v-model="business.key"
                prepend-icon="mdi-format-list-bulleted-square"
              ></v-text-field>
            </v-col>
            <v-col cols="5" sm="5">
              <v-text-field
                rounded
                outlined
                label="Value"
                v-model="business.value"
              ></v-text-field>
            </v-col>
            <v-col cols="2" sm="2">
              <v-btn
                class="mt-2"
                @click="addBusiness"
                color="primary"
                style="color: white"
              >
                Add property
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
      <v-card class="mx-auto" max-width="344" outlined>
        <v-list-item three-line>
          <v-list-item-content>
            <div class="overline mb-1">Log Preview</div>
            <v-list-item-title class="mb-1">
              message : {{ log.message }}
            </v-list-item-title>
            <v-list-item-subtitle>{{
              log.businessProperties
            }}</v-list-item-subtitle>
          </v-list-item-content>

          <v-list-item-avatar tile size="80" :color="getLogColor()"
            >{{ log.level }}
          </v-list-item-avatar>
        </v-list-item>

        <v-card-actions>
          <v-btn rounded depressed color="primary" @click="sendCustomLog">
            Send Log
          </v-btn>
          <v-btn rounded depressed color="secondary" @click="cleanLog">
            Clean log
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>
<script lang="ts">
import Vue from "vue";
import { Component } from "vue-property-decorator";

import * as Faker from "faker";
import { LogLevel, logLevelColor } from "~/models/LogLevel";
import { demo } from "~/utils/store-accessor";

@Component({
  name: "Index",
})
export default class index extends Vue {
  currentUser = {
    firstName: "",
    lastName: "",
    email: "",
  };
  business: any = {
    key: "",
    value: "",
  };
  log: any = {
    message: "",
    level: LogLevel.ERROR,
    businessProperties: {},
  };
  levels = [LogLevel.INFO, LogLevel.WARN, LogLevel.DEBUG, LogLevel.ERROR];

  async mounted() {
    await demo.updateUser();
  }
  get users() {
    return demo.users;
  }
  generateUsers(user: number) {
    const usersRandom = [];
    for (let i = 0; i < user; i++) {
      let firstName = Faker.name.firstName();
      let lastName = Faker.name.lastName();
      usersRandom.push({
        firstName: firstName,
        lastName: lastName,
        email:
          firstName.toLowerCase() +
          "." +
          lastName.toLowerCase() +
          "@fakemail.com",
      });
    }
    demo.addUsers(usersRandom);
  }
  addUser() {
    demo.addUser(this.currentUser);
    this.currentUser = {
      firstName: "",
      lastName: "",
      email: "",
    };
  }
  removeUser(user: any) {
    demo.deleteUser(user.id);
  }
  deleteAllUser() {
    demo.deleteAll();
  }
  addBusiness() {
    (this.log.businessProperties as any)[this.business.key] =
      this.business.value;
    this.business = {
      key: "",
      value: "",
    };
  }

  getLogColor() {
    return logLevelColor(this.log.level);
  }
  sendCustomLog() {
    demo.sendCustomLog(this.log);
    this.cleanLog();
  }
  cleanLog() {
    this.business = {
      key: "",
      value: "",
    };
    this.log = {
      message: "",
      level: LogLevel.ERROR,
      businessProperties: {},
    };
  }
}
</script>
<style scoped></style>
