<script setup lang="ts">
import { NodeViewContent, nodeViewProps, NodeViewWrapper } from "@tiptap/vue-3";
import { ref, computed } from "vue";
import { Button } from "@/components/ui/button";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "~/lib/utils";
import { Icon } from "@/components/ui/icon";
import { onClickOutside } from "@vueuse/core";
import { toast } from "~/components/ui/toast";

const props = defineProps(nodeViewProps);

const open = ref(false);
const searchQuery = ref("");

const languages = ref(props.extension.options.lowlight.listLanguages());

const filteredLanguages = computed(() => {
  return languages.value.filter((language: string) =>
    language.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const filterLanguages = (list: string[], term: string) => {
  return list.filter((language) =>
    language.toLowerCase().includes(term.toLowerCase())
  );
};

const selectedLanguage = computed({
  get: () => props.node.attrs.language as string | undefined,
  set: (language: string | undefined) => {
    props.updateAttributes({ language });
  },
});

const toggleOpen = () => {
  open.value = !open.value;
};

const setOpen = (newState: boolean) => {
  open.value = newState;
};

const selectLanguage = (language: string) => {
  selectedLanguage.value = language;
  open.value = false;
};

const filterProgrammingLanguages = (
  list: string[] | number[] | false[] | true[] | Record<string, any>[],
  term: string
): string[] | number[] | false[] | true[] | Record<string, any>[] =>
  filterLanguages(list as string[], term);

const popoverRef = ref(null);
onClickOutside(popoverRef, () => {
  open.value = false;
});

const copyCode = async () => {
  try {
    const codeElement =
      popoverRef.value && (popoverRef.value as HTMLElement).closest
        ? (popoverRef.value as HTMLElement)
            .closest(".relative")
            ?.querySelector("pre code")
        : null;
    if (codeElement) {
      await navigator.clipboard.writeText(codeElement.textContent || "");
      toast({
        title: "Copied!",
        description: "Code has been copied to clipboard.",
        duration: 2000,
      });
    }
  } catch (error) {
    console.error("Failed to copy code:", error);
  }
};
</script>

<template>
  <node-view-wrapper class="relative group">
    <Popover :open="open" @onOpenChange="setOpen">
      <div
        class="absolute top-1 right-1 flex space-x-2 opacity-0 group-hover:opacity-100 transition-opacity"
        ref="popoverRef"
      >
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            size="xs"
            role="combobox"
            :aria-expanded="open"
            class="max-w-full justify-between"
            @click="toggleOpen"
          >
            {{ selectedLanguage || "Select language..." }}
            <Icon
              name="radix-icons:caret-sort"
              color="black"
              class="ml-auto h-4 w-4 shrink-0 opacity-50"
            />
          </Button>
        </PopoverTrigger>
        <Button variant="outline" size="xs" @click="copyCode">
          <Icon
            name="radix-icons:copy"
            color="black"
            class="h-4 w-4 shrink-0 opacity-50"
          />
        </Button>
      </div>
      <PopoverContent class="w-48 p-0">
        <Command :filter-function="filterProgrammingLanguages">
          <CommandInput
            v-model="searchQuery"
            placeholder="Search language..."
          />
          <CommandEmpty v-if="filteredLanguages.length === 0">
            No language found.
          </CommandEmpty>
          <CommandList>
            <CommandGroup>
              <CommandItem
                v-for="language in filteredLanguages"
                :key="language"
                :value="language"
                @click="selectLanguage(language)"
              >
                <Icon
                  name="radix-icons:check"
                  color="black"
                  :class="
                    cn(
                      'h-4 w-4',
                      selectedLanguage === language
                        ? 'opacity-100'
                        : 'opacity-0'
                    )
                  "
                />
                {{ language }}
              </CommandItem>
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
    <pre><code><node-view-content /></code></pre>
  </node-view-wrapper>
</template>
