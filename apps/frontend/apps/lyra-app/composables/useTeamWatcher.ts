import { watch } from 'vue';
import { storeToRefs } from '#imports';
import { useWorkspaceStore } from '~/store/workspace.store';

export function useTeamWatcher(callback: () => Promise<void>) {
  const workspaceStore = useWorkspaceStore();
  const { selectedTeam } = storeToRefs(workspaceStore);

  watch(selectedTeam, async () => {
    await callback();
  });
}
