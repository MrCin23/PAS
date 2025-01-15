import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vite.dev/config/
export default defineConfig({
  // server: {
  //   proxy: {
  //     '/api': {
  //       target: 'https://flounder-sunny-goldfish.ngrok-free.app/REST/api',
  //       changeOrigin: true,
  //       rewrite: (path) => path.replace(/^\https://flounder-sunny-goldfish.ngrok-free.app/REST/api/, '')
  //     }
  //   }
  // },
  build: {
    sourcemap: true, //add this property to enable browser code visibility. Needed for React dev tools plugin.
  },
  plugins: [react()],
});
