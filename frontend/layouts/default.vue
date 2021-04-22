<template>
  <v-app>
    <logub-drawer/>

    <v-main>
      <v-container fluid>
        <nuxt/>
      </v-container>
    </v-main>

    <v-footer app>
      <span class="mx-auto">&copy; Logub - Build on Redis Hackathon - {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component } from 'nuxt-property-decorator';
import LogubDrawer from '~/components/navigation/LogubDrawer.vue';

@Component({
  name: "Default",
  components: { LogubDrawer },
})
export default class Default extends Vue {
}
</script>
