import react from '@vitejs/plugin-react';
import { defineConfig } from 'vite';
import tsconfigPaths from 'vite-tsconfig-paths';

// https://vitejs.dev/config/
export default defineConfig({
  base: "http://localhost:8081/resources/react/",
  build: {
    sourcemap: true,
  },
  plugins: [react(), tsconfigPaths()],
  css: {
    devSourcemap: true,
  },
});
