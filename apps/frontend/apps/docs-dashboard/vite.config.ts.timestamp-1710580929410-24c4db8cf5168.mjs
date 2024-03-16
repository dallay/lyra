// vite.config.ts
import Vue from 'file:///Users/acosta/Dev/lyra/apps/frontend/node_modules/.pnpm/@vitejs+plugin-vue@5.0.4_vite@5.1.6_vue@3.4.21/node_modules/@vitejs/plugin-vue/dist/index.mjs';
import envify from 'file:///Users/acosta/Dev/lyra/apps/frontend/node_modules/.pnpm/process-envify@2.0.0/node_modules/process-envify/dist/process-envify.js';
import unocss from 'file:///Users/acosta/Dev/lyra/apps/frontend/node_modules/.pnpm/unocss@0.58.6_postcss@8.4.35_vite@5.1.6/node_modules/unocss/dist/vite.mjs';
import {
	defineConfig,
	mergeConfig,
} from 'file:///Users/acosta/Dev/lyra/apps/frontend/node_modules/.pnpm/vite@5.1.6_@types+node@20.11.28_sass@1.72.0/node_modules/vite/dist/node/index.js';
import vueRoutes from 'file:///Users/acosta/Dev/lyra/apps/frontend/node_modules/.pnpm/vite-plugin-vue-routes@1.1.0/node_modules/vite-plugin-vue-routes/dist/vite-plugin-vue-routes.js';
import { sharedViteConfig } from 'file:///Users/acosta/Dev/lyra/apps/frontend/config/vite.config.shared.mjs';
var __vite_injected_original_dirname = '/Users/acosta/Dev/lyra/apps/frontend/apps/docs-dashboard';
var vite_config_default = mergeConfig(
	sharedViteConfig(__vite_injected_original_dirname),
	defineConfig({
		define: envify({
			API_URL: process.env.API_URL || '',
		}),
		plugins: [Vue(), vueRoutes(), unocss({})],
		server: {
			port: 8978,
			proxy: {
				'/api': {
					target: 'http://127.0.0.1:3000',
					ws: true,
				},
			},
		},
	})
);
export { vite_config_default as default };
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCIvVXNlcnMvYWNvc3RhL0Rldi9seXJhL2FwcHMvZnJvbnRlbmQvYXBwcy9kb2NzLWRhc2hib2FyZFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiL1VzZXJzL2Fjb3N0YS9EZXYvbHlyYS9hcHBzL2Zyb250ZW5kL2FwcHMvZG9jcy1kYXNoYm9hcmQvdml0ZS5jb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL1VzZXJzL2Fjb3N0YS9EZXYvbHlyYS9hcHBzL2Zyb250ZW5kL2FwcHMvZG9jcy1kYXNoYm9hcmQvdml0ZS5jb25maWcudHNcIjtpbXBvcnQgVnVlIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZSc7XG5pbXBvcnQgZW52aWZ5IGZyb20gJ3Byb2Nlc3MtZW52aWZ5JztcbmltcG9ydCB1bm9jc3MgZnJvbSAndW5vY3NzL3ZpdGUnO1xuaW1wb3J0IHsgZGVmaW5lQ29uZmlnLCBtZXJnZUNvbmZpZyB9IGZyb20gJ3ZpdGUnO1xuaW1wb3J0IHZ1ZVJvdXRlcyBmcm9tICd2aXRlLXBsdWdpbi12dWUtcm91dGVzJztcbmltcG9ydCB7IHNoYXJlZFZpdGVDb25maWcgfSBmcm9tICdAbHlyYS9jb25maWcvdml0ZS5jb25maWcuc2hhcmVkJztcblxuZXhwb3J0IGRlZmF1bHQgbWVyZ2VDb25maWcoXG5cdHNoYXJlZFZpdGVDb25maWcoX19kaXJuYW1lKSxcblx0ZGVmaW5lQ29uZmlnKHtcblx0XHRkZWZpbmU6IGVudmlmeSh7XG5cdFx0XHRBUElfVVJMOiBwcm9jZXNzLmVudi5BUElfVVJMIHx8ICcnLFxuXHRcdH0pLFxuXHRcdHBsdWdpbnM6IFtWdWUoKSwgdnVlUm91dGVzKCksIHVub2Nzcyh7fSldLFxuXHRcdHNlcnZlcjoge1xuXHRcdFx0cG9ydDogODk3OCxcblx0XHRcdHByb3h5OiB7XG5cdFx0XHRcdCcvYXBpJzoge1xuXHRcdFx0XHRcdHRhcmdldDogJ2h0dHA6Ly8xMjcuMC4wLjE6MzAwMCcsXG5cdFx0XHRcdFx0d3M6IHRydWUsXG5cdFx0XHRcdH0sXG5cdFx0XHR9LFxuXHRcdH0sXG5cdH0pXG4pO1xuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUEwVixPQUFPLFNBQVM7QUFDMVcsT0FBTyxZQUFZO0FBQ25CLE9BQU8sWUFBWTtBQUNuQixTQUFTLGNBQWMsbUJBQW1CO0FBQzFDLE9BQU8sZUFBZTtBQUN0QixTQUFTLHdCQUF3QjtBQUxqQyxJQUFNLG1DQUFtQztBQU96QyxJQUFPLHNCQUFRO0FBQUEsRUFDZCxpQkFBaUIsZ0NBQVM7QUFBQSxFQUMxQixhQUFhO0FBQUEsSUFDWixRQUFRLE9BQU87QUFBQSxNQUNkLFNBQVMsUUFBUSxJQUFJLFdBQVc7QUFBQSxJQUNqQyxDQUFDO0FBQUEsSUFDRCxTQUFTLENBQUMsSUFBSSxHQUFHLFVBQVUsR0FBRyxPQUFPLENBQUMsQ0FBQyxDQUFDO0FBQUEsSUFDeEMsUUFBUTtBQUFBLE1BQ1AsTUFBTTtBQUFBLE1BQ04sT0FBTztBQUFBLFFBQ04sUUFBUTtBQUFBLFVBQ1AsUUFBUTtBQUFBLFVBQ1IsSUFBSTtBQUFBLFFBQ0w7QUFBQSxNQUNEO0FBQUEsSUFDRDtBQUFBLEVBQ0QsQ0FBQztBQUNGOyIsCiAgIm5hbWVzIjogW10KfQo=
