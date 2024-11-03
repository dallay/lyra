<script setup lang="ts">
import { ref } from "vue";
import { cn } from "~/lib/utils";
import { Popover, PopoverContent, PopoverTrigger } from "~/components/ui/popover";
import { Card, CardContent } from "~/components/ui/card";
import { Icon } from "~/components/ui/icon";
import { Button } from "@/components/ui/button";
import { ScrollArea } from '@/components/ui/scroll-area';
import { Input } from '@/components/ui/input';
import { Separator } from "@/components/ui/separator";
import { ColorPicker } from '~/components/ui/color-picker';
import MenuButton from "~/components/editor/extensions/menus/MenuButton.vue";

type ViewStates = 'main' | 'fontFamily' | 'colorPicker'

export interface TextStylesPopoverProps {
  icon: string;
  label: string;
}

const props = defineProps<TextStylesPopoverProps>();
const menuOpen = ref(false);

const colorOptions = ['#FF00FF', '#FFFFFF', '#E0E0E0', '#808080', '#C0C0C0', '#F0F0F0']

const defaultColor = colorOptions[0]

const view = ref<ViewStates>('main')
const fontFamily = ref('None')
const fontSize = ref('Default')
const textColor = ref(defaultColor)
const selectedColors = ref<string[]>([])
const tempColor = ref(defaultColor)

const fontSizes = [
  { value: 'default', label: 'Default' },
  { value: 'small', label: 'Small' },
  { value: 'medium', label: 'Medium' },
  { value: 'large', label: 'Large' },
  { value: 'x-large', label: 'X-Large' },
  { value: 'huge', label: 'Huge' }
]

const fontFamilies = [
  { value: 'none', label: 'None' },
  { value: 'helvetica', label: 'Helvetica' },
  { value: 'arial', label: 'Arial' },
  { value: 'courier', label: 'Courier' },
  { value: 'didot', label: 'Didot' },
  { value: 'garamond', label: 'Garamond' }
]


const addSelectedColor = (color: string) => {
  if (!selectedColors.value.includes(color)) {
    if (selectedColors.value.length >= 5) {
      selectedColors.value.shift();
    }
    selectedColors.value.push(color);
  }
}

const showColorPicker = () => {
  view.value = 'colorPicker';
  tempColor.value = textColor.value;
}

const onTextColorChange = (color: string) => {
  tempColor.value = color;
}

const setColor = () => {
  textColor.value = tempColor.value;
  addSelectedColor(tempColor.value);
  view.value = 'main';
}

const selectFontFamily = (font: { value: string, label: string }) => {
  fontFamily.value = font.label
  view.value = 'main'
}

const resetColors = () => {
  selectedColors.value = [];
}
</script>

<template>
  <Popover v-model:open="menuOpen" asChild>
    <PopoverTrigger>
      <MenuButton :icon="props.icon" :label="props.label" >
        <template #icon-after>
          <Icon name="ChevronDown" class="w-2 h-2" />
        </template>
      </MenuButton>
    </PopoverTrigger>
    <PopoverContent class="w-full h-full p-0" side="bottom" :sideOffset="8" asChild>
      <Card class="p-1">
        <CardContent class="p-0">
          <ScrollArea class="min-h-72">
            <div v-if="view === 'main'" class="space-y-4 p-1">
              <div class="space-y-2">
                <Button variant="ghost" class="w-full flex items-center justify-between text-sm text-gray-300" @click="view = 'fontFamily'">
                  <span>Font family override</span>
                  <div class="flex items-center gap-2 text-gray-400">
                    <span>{{ fontFamily }}</span>
                    <Icon name="ChevronRight" class="h-4 w-4" />
                  </div>
                </Button>
              </div>

              <div class="space-y-2">
                <span class="text-sm text-gray-300">Font size</span>
                <div class="grid grid-cols-3 gap-2">
                  <Button
                    v-for="size in fontSizes"
                    :key="size.value"
                    variant="ghost"
                    :class="cn('px-3 py-1 text-sm rounded-md',
                      fontSize === size.label ? 'bg-gray-700 text-white': 'text-gray-400 hover:bg-gray-800',
                    )"
                    @click="fontSize = size.label"
                  >
                    {{ size.label }}
                  </Button>
                </div>
              </div>

              <div class="space-y-2">
                <span class="text-sm text-gray-300">Text color</span>
                <div class="grid grid-cols-6 gap-2">
                  <button
                    v-for="color in colorOptions"
                    :key="color"
                    :class="cn('size-5 rounded-full border-2',
                      textColor === color ? 'border-white': 'border-transparent'
                    )"
                    :style="{ backgroundColor: color }"
                    @click="textColor = color"
                  />
                </div>
                <Separator class="my-1"/>
                <div class="grid grid-cols-6 gap-2">
                  <button
                    class="size-5 rounded-full bg-gradient-to-br from-pink-500 via-blue-500 to-green-500"
                    @click="showColorPicker"
                  />
                  <button
                    v-for="color in selectedColors"
                    :key="color"
                    :class="cn('size-5 rounded-full border-2',
                      textColor === color ? 'border-white': '',
                      textColor !== color ? 'border-transparent': '',
                    )"
                    :style="{ backgroundColor: color }"
                    @click="textColor = color"
                  />
                  <Button
                    variant="ghost"
                    size="icon"
                    class="size-5 rounded-full border-2 border-transparent"
                    @click="resetColors"
                  >
                    <Icon name="Ban" class="h-4 w-4" />
                  </Button>
                </div>
              </div>
            </div>

            <div v-else-if="view === 'colorPicker'" class="p-1">
              <Button variant="ghost" @click="view = 'main'" class="w-full flex items-center justify-start">
                  <Icon name="ChevronLeft" class="h-4 w-4" />
                  <span class="text-sm text-gray-300">
                    Text color
                  </span>
                </Button>
                <Separator class="my-1"/>
              <ColorPicker
                :color="tempColor"
                @update:color="onTextColorChange"
              />
              <Separator class="my-1"/>
                <Button v-if="tempColor !== textColor" variant="ghost" class="w-full flex items-center justify-start" @click="setColor">
                Set Color - {{ tempColor }} - {{ textColor }}
                </Button>
            </div>

            <div v-else class="p-1">
              <div class="sticky top-0 z-10">
                <Button variant="ghost" @click="view = 'main'" class="w-full flex items-center justify-start">
                  <Icon name="ChevronLeft" class="h-4 w-4" />
                  <span class="text-sm text-gray-300">Font family</span>
                </Button>
                <Separator class="my-1"/>
                <Input
                  class="bg-gray-800 border-gray-700 text-gray-300 mb-2"
                  placeholder="Filter font family"
                />
              </div>
              <div class="space-y-1">
                <Button
                  v-for="font in fontFamilies"
                  :key="font.value"
                  variant="ghost"
                  :class="cn('w-full flex items-center justify-start px-3 py-2 rounded-md text-sm',
                    fontFamily === font.label ? 'bg-gray-700 text-white': 'text-gray-300 hover:bg-gray-800',
                  )"
                  @click="selectFontFamily(font)"
                >
                  {{ font.label }}
                </Button>
              </div>
            </div>
          </ScrollArea>
        </CardContent>
      </Card>
    </PopoverContent>
  </Popover>
</template>
