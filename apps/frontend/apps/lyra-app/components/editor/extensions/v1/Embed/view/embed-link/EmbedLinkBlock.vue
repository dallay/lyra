<template>
  <NodeViewWrapper>
    <div class="w-full max-w-4xl mx-auto">
      <Card>
        <div
          :class="
            cn(
              'flex flex-col',
              props.node.attrs.layout === 'right'
                ? 'md:flex-row-reverse'
                : props.node.attrs.layout === 'left'
                ? 'md:flex-row'
                : props.node.attrs.layout === 'top'
                ? 'flex-col'
                : ''
            )
          "
        >
          <div
            :class="
              cn(
                'flex-grow justify-center items-center',
                props.node.attrs.layout === 'top'
                  ? 'w-full p-2'
                  : 'w-full md:w-1/3 p-0 h-full'
              )
            "
          >
            <AspectRatio :ratio="16 / 9" class="bg-muted h-full">
              <NuxtImg
                :src="props.node.attrs.image"
                :alt="props.node.attrs.title"
                class="rounded-md object-cover"
                fit="cover"
                preload
                loading="lazy"
              />
            </AspectRatio>
          </div>
          <div
            :class="
              cn(
                props.node.attrs.layout === 'top'
                  ? 'w-full'
                  : 'w-full md:w-1/2'
              )
            "
          >
            <CardContent>
              <h3 class="line-clamp-1">{{ props.node.attrs.title }}</h3>
              <p class="line-clamp-2">{{ props.node.attrs.description }}</p>
              <a
                :href="props.node.attrs.url"
                target="_blank"
                rel="noopener noreferrer"
                class="line-clamp-1 block text-xs font-sans hover:underline overflow-hidden"
              >
                {{ props.node.attrs.url }}
              </a>
            </CardContent>
          </div>
        </div>
      </Card>
    </div>
  </NodeViewWrapper>
</template>

<script setup lang="ts">
import { cn } from "~/lib/utils";
import { nodeViewProps, NodeViewWrapper } from "@tiptap/vue-3";
import { Card, CardContent } from "@/components/ui/card";
import { AspectRatio } from "@/components/ui/aspect-ratio";

const props = defineProps(nodeViewProps);
</script>
