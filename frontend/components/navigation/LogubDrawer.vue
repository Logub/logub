<template>
  <v-navigation-drawer app permanent color="primary">
    <v-list-item class="pa-0">
      <v-list-item-content class="pa-0">
        <v-img
          :src="require('../../assets/images/logo.png')"
          width="250"
          contain
        />
      </v-list-item-content>
    </v-list-item>

    <v-list dense nav>
      <v-list-item
        v-for="route in routes"
        :key="route.title"
        :to="route.link"
        color="white"
        nuxt
      >
        <v-list-item-icon class="ml-3 mr-5">
          <v-icon color="info">{{ route.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title class="font-weight-regular info--text">{{ route.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <template v-slot:append>
      <div class="px-6 pb-7">
        <v-btn block :color="'#039BE5'" style="color: white" :to="{'name':'demo'}" target="_blank">
          Try it now !
        </v-btn>
      </div>
    </template>
  </v-navigation-drawer>
</template>
<script lang="ts">
import Vue from 'vue';
import {Component} from 'nuxt-property-decorator';

@Component({
  name: "LogubDrawer"
})
export default class LogubDrawer extends Vue {
  private readonly routes = [
    {
      link: '/',
      icon: 'mdi-access-point',
      title: 'Overview'
    }
  ];
}
</script>
<style scoped>
.v-list--nav .v-list-item, .v-list--nav .v-list-item:before {
  border-radius: 0 !important;
}
</style>
