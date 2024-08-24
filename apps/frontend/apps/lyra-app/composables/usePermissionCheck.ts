import { ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';

export function usePermissionCheck(to: string | undefined) {
  const hasPermission = ref(true);
  function checkPermissions(route: string | undefined): boolean {
    return true; // TODO: Implement permission check
  }

  watchEffect(() => {
    hasPermission.value = checkPermissions(to);
  });

  return { hasPermission };
}
