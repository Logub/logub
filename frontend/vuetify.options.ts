import colors from 'vuetify/es5/util/colors';
import { UserVuetifyPreset } from 'vuetify';

const vuetifyOptions: UserVuetifyPreset = {
  theme: {
    dark: false,
    themes: {
      light: {
        primary: "#2a5f8c",
        secondary: '#424242',
        accent: '#82B1FF',
        error: '#FF5252',
        info: '#FEFEFE',
        success: '#4CAF50',
        warning: '#FFC107',
      }
    }
  },
};

export default vuetifyOptions;
