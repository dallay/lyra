<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {storeToRefs} from 'pinia';
import {useWorkspaceStore} from '~/store/workspace.store';
import { useTeamStore} from "~/store/team.store";
import {cn} from '@/lib/utils';
import {Avatar, AvatarFallback, AvatarImage,} from '@/components/ui/avatar';
import {Button} from '@/components/ui/button';
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  CommandSeparator
} from '@/components/ui/command';
import {Popover, PopoverContent, PopoverTrigger,} from '@/components/ui/popover';
import {generateRandomWords, initials} from '#imports'
import { toTypedSchema } from '@vee-validate/zod'
import * as z from 'zod'
import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from '@/components/ui/form'
import {toast} from "~/components/ui/toast";
import {OrganizationId, TeamId} from "@lyra/domain";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';

interface OrganizationSwitcherProps {
  isCollapsed: boolean
}

const props = withDefaults(defineProps<OrganizationSwitcherProps>(), {
  isCollapsed: false,
})

const formSchema = toTypedSchema(
  z.object({
    name: z.string().min(2).max(50).default(generateRandomWords()),
  })
);

const store = useWorkspaceStore();
const {fetchWorkspaces, setSelectedTeam, getWorkspaceByTeamId} = store;
const { groupedWorkspace, selectedTeam } = storeToRefs(store);
const teamStore = useTeamStore();
const { createTeam } = teamStore;

const open = ref(false);
const showNewTeamDialog = ref(false);


const onSubmit = async (values: any) => {
  try {
    const { teamId } = selectedTeam.value ?? {};
    if (!teamId) {
      throw new Error('No team selected.');
    }

    const workspace = getWorkspaceByTeamId(teamId);
    const organizationId = workspace?.organizationId;

    if (!organizationId) {
      throw new Error('No organization found for the selected team.');
    }

    await createTeam({
      organizationId: OrganizationId.create(organizationId),
      teamId: TeamId.random(),
      name: values.name,
    });

    showNewTeamDialog.value = false;

    toast({
      title: 'Team created!',
      description: 'You have successfully created a new team.',
      duration: 5000,
    });
  } catch (error) {
    toast({
      title: 'Error',
      description: error instanceof Error ? error.message : 'An unexpected error occurred while creating the team.',
      duration: 5000,
      variant: 'destructive',
    });
    console.error('onSubmit error:', error);
  }
};

onMounted(async () => {
  await fetchWorkspaces();
});
</script>

<template>
  <Dialog v-model:open="showNewTeamDialog">
    <Popover v-model:open="open">
      <PopoverTrigger as-child>
        <Button
          :variant="'outline'"
          :role="'combobox'"
          :aria-expanded="open"
          :aria-label="'Select a team'"
          :class="cn('w-full justify-between', $attrs.class ?? '')"
        >
          <Avatar class="mr-2 h-5 w-5">
            <AvatarImage
              :src="`https://avatar.vercel.sh/${selectedTeam?.teamId}.png`"
              :alt="selectedTeam?.teamName"
            />
            <AvatarFallback>{{ initials(selectedTeam?.teamName) }}</AvatarFallback>
          </Avatar>
          <template v-if="!isCollapsed">
            {{ selectedTeam?.teamName }}
            <Icon
              name="radix-icons:caret-sort"
              color="black"
              class="ml-auto h-4 w-4 shrink-0 opacity-50"
            />
          </template>
        </Button>
      </PopoverTrigger>
      <PopoverContent class="w-[200px] p-0">
        <Command :filter-function="(list, term) => list.filter(i => i.teamName?.toLowerCase()?.includes(term))">
          <CommandList>
            <CommandInput placeholder="Search team..." />
            <CommandEmpty>No team found.</CommandEmpty>
            <CommandGroup v-for="group in groupedWorkspace" :key="group.organizationName" :heading="group.organizationName">
              <CommandItem
                v-for="team in group.teams"
                :key="team.teamId"
                :value="team"
                class="text-sm"
                @select="() => {
                  selectedTeam = team
                  open = false
                }"
              >
                <Avatar class="mr-2 h-5 w-5">
                  <AvatarImage
                    :src="`https://avatar.vercel.sh/${team.teamId}.png`"
                    :alt="team.teamName"
                    class="grayscale"
                  />
                  <AvatarFallback>{{ initials(team.teamName)}}</AvatarFallback>
                </Avatar>
                {{ team.teamName }}
                <Icon name="radix-icons:check" color="black" :class="cn('ml-auto h-4 w-4',
                             selectedTeam?.teamId === team.teamId
                               ? 'opacity-100'
                               : 'opacity-0',
                  )" />

              </CommandItem>
            </CommandGroup>
          </CommandList>
          <CommandSeparator />
          <CommandList>
            <CommandGroup>
              <DialogTrigger as-child>
                <CommandItem
                  value="create-team"
                  @select="() => {
                    open = false
                    showNewTeamDialog = true
                  }"
                >
                  <Icon name="radix-icons:plus-circled" color="black" class="mr-2 h-5 w-5" />
                  Create Team
                </CommandItem>
              </DialogTrigger>
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
    <DialogContent class="sm:max-w-[425px]">
      <DialogHeader>
        <DialogTitle>Create team</DialogTitle>
        <DialogDescription>
          Add a new team to your organization.
        </DialogDescription>
      </DialogHeader>
      <Form
        :validation-schema="formSchema"
        class="space-y-6"
        @submit="onSubmit"
      >
        <FormField v-slot="{ componentField }" name="name">
          <FormItem>
            <FormLabel>Team Name</FormLabel>
            <FormControl>
              <Input type="text" placeholder="Acme Inc." v-bind="componentField" />
            </FormControl>
            <FormDescription>
              This is your public display name.
            </FormDescription>
            <FormMessage />
          </FormItem>
        </FormField>
        <Button type="submit" class="w-full"> Create team </Button>
      </Form>
    </DialogContent>
  </Dialog>
</template>
