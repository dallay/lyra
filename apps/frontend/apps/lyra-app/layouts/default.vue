<script lang="ts" setup>
import { cn } from "@/lib/utils";
import { ref } from "vue";
import { refDebounced } from "@vueuse/core";
import { Separator } from "@/components/ui/separator";
import { TooltipProvider } from "@/components/ui/tooltip";
import {
  ResizableHandle,
  ResizablePanel,
  ResizablePanelGroup,
} from "@/components/ui/resizable";
import Nav from "~/components/nav/Navbar.vue";
import UserNav from "~/components/nav/UserNav.vue";
import OrganizationSwitcher from "~/components/OrganizationSwitcher.vue";
import MainNav from "~/components/nav/MainNav.vue";
import ThemeSwitcher from "~/components/ThemeSwitcher.vue";
import StartWriting from "~/components/nav/StartWriting.vue";
import { links, links2 } from "~/components/nav/links-list";

interface DefaultLayoutProps {
  defaultLayout?: number[];
  defaultCollapsed?: boolean;
  navCollapsedSize: number;
}

const props = withDefaults(defineProps<DefaultLayoutProps>(), {
  defaultCollapsed: false,
  defaultLayout: () => [265, 1095],
});

const isCollapsed = ref(props.defaultCollapsed);
const searchValue = ref("");
const debouncedSearch = refDebounced(searchValue, 250);

function onCollapse() {
  isCollapsed.value = true;
}

function onExpand() {
  isCollapsed.value = false;
}
</script>

<template>
  <TooltipProvider :delay-duration="0">
    <ResizablePanelGroup
      id="resize-panel-group-1"
      direction="horizontal"
      class="h-full max-h-screen items-stretch"
    >
      <ResizablePanel
        id="resize-panel-1"
        :default-size="defaultLayout[0]"
        :collapsed-size="navCollapsedSize"
        collapsible
        :min-size="15"
        :max-size="20"
        :class="cn(isCollapsed && 'min-w-[50px] transition-all duration-300 ease-in-out')"
        @expand="onExpand"
        @collapse="onCollapse"
        class="sticky top-0 h-screen"
      >
        <div class="flex flex-col h-full">
          <div
            :class="
              cn(
                'flex h-[52px] items-center justify-center',
                isCollapsed ? 'h-[52px]' : 'px-2'
              )
            "
          >
            <OrganizationSwitcher :is-collapsed="isCollapsed" />
          </div>
          <Separator />
          <Nav :is-collapsed="isCollapsed" :links="links">
            <template #start-writing="{ link, isCollapsed }">
              <StartWriting />
            </template>
          </Nav>
          <Separator />
          <Nav :is-collapsed="isCollapsed" :links="links2" />

          <div class="mt-auto p-4" v-if="!isCollapsed">
            <Card>
              <CardHeader class="p-2 pt-0 md:p-4">
                <CardTitle>Upgrade to Pro</CardTitle>
                <CardDescription>
                  Unlock all features and get unlimited access to our support team.
                </CardDescription>
              </CardHeader>
              <CardContent class="p-2 pt-0 md:p-4 md:pt-0">
                <Button size="sm" class="w-full"> Upgrade </Button>
              </CardContent>
            </Card>
          </div>
          <Nav
            :is-collapsed="isCollapsed"
            :links="[
              {
                id: 'settings',
                title: 'Settings',
                label: '972',
                icon: 'lucide:settings',
                variant: 'ghost',
              },
            ]"
          />
        </div>
      </ResizablePanel>
      <ResizableHandle id="resize-handle-1" with-handle />
      <ResizablePanel id="resize-panel-2" :default-size="defaultLayout[1]" :min-size="30">
        <div class="border-b">
          <div class="flex h-16 items-center px-4">
            <MainNav class="mx-6" />
            <div class="ml-auto flex items-center space-x-4">
              <Search />
              <ThemeSwitcher />
              <UserNav />
            </div>
          </div>
        </div>
        <div class="h-screen max-h-screen overflow-auto p-2">
          <slot />
        </div>
      </ResizablePanel>
    </ResizablePanelGroup>
  </TooltipProvider>
</template>
