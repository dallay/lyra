<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { useCookie } from "#app";
import { storeToRefs } from "pinia";
import { useAuthStore } from "~/store/auth.store";
import { cn } from "@/lib/utils";
import { useRouter } from "vue-router";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useForm } from "vee-validate";
import { toTypedSchema } from "@vee-validate/zod";
import * as z from "zod";

import {
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { toast } from "~/components/ui/toast";

const store = useAuthStore();
const { authenticateUser, refreshToken } = store;
const router = useRouter();

const formSchema = toTypedSchema(
  z.object({
    identifier: z
      .string()
      .min(2, "Identifier must be at least 2 characters long.")
      .max(100),
    password: z
      .string()
      .min(8, "Password must be at least 8 characters long.")
      .max(250),
  })
);

const form = useForm({
  validationSchema: formSchema,
});

const { accessToken, loading, isAuthenticated } = storeToRefs(store);
const isLoading = ref(loading.value);

const redirectCookie = useCookie("redirectPath");

const handleAuthentication = async (values: {
  identifier: string;
  password: string;
}) => {
  const { identifier, password } = values;
  await authenticateUser({ identifier, password });

  if (isAuthenticated.value) {
    toast({
      title: "Welcome back!",
      description: `You are now authenticated.`,
      duration: 5000,
    });

    const redirectPath = redirectCookie.value || "/";
    redirectCookie.value = null;
    await router.push(redirectPath);
  } else {
    toast({
      title: "Authentication failed!",
      description: "Please check your credentials and try again.",
      duration: 5000,
      variant: "destructive",
    });
  }
};

const onSubmit = form.handleSubmit(handleAuthentication);

watch([isAuthenticated, accessToken], async ([isAuthenticated]) => {
  if (isAuthenticated && router.currentRoute.value.name !== "login") {
    const redirectPath = redirectCookie.value || "/";
    redirectCookie.value = null;
    await router.push(redirectPath);
  }
});

onMounted(async () => {
  if (!isAuthenticated.value && !accessToken.value) {
    await refreshToken();
  }
});
</script>

<template>
  <div :class="cn('grid gap-6', $attrs.class ?? '')">
    <form @submit="onSubmit">
      <div class="grid gap-2">
        <div class="grid gap-1">
            <FormField v-slot="{ componentField }" name="identifier">
            <FormItem>
              <FormLabel>Username or Email</FormLabel>
              <FormControl>
              <Input
                type="text"
                placeholder="Username or Email"
                autocomplete="username"
                v-bind="componentField"
              />
              </FormControl>
              <FormDescription> Enter your username or email. </FormDescription>
              <FormMessage />
            </FormItem>
            </FormField>
          <FormField v-slot="{ componentField }" name="password">
            <FormItem>
              <FormLabel>Password</FormLabel>
              <FormControl>
                <Input
                  type="password"
                  placeholder="Enter your password"
                  autocomplete="current-password"
                  v-bind="componentField"
                />
              </FormControl>
              <FormDescription>
                Password must be at least 8 characters long.
              </FormDescription>
              <FormMessage />
            </FormItem>
          </FormField>
        </div>
        <Button :disabled="isLoading" type="submit">
          <Icon
            v-if="isLoading"
            name="tabler:loader-2"
            color="black"
            class="mr-2 h-4 w-4 animate-spin"
          />
          Sign In
        </Button>
      </div>
    </form>
    <div class="relative">
      <div class="absolute inset-0 flex items-center">
        <span class="w-full border-t" />
      </div>
      <div class="relative flex justify-center text-xs uppercase">
        <span class="bg-background px-2 text-muted-foreground">
          Or continue with
        </span>
      </div>
    </div>
    <Button variant="outline" type="button" :disabled="isLoading">
      <Icon
        v-if="isLoading"
        name="mdi:loading"
        color="black"
        class="mr-2 h-4 w-4 animate-spin"
      />
      <Icon v-else name="hugeicons:github" color="black" class="mr-2 h-4 w-4" />
      GitHub
    </Button>
  </div>
</template>
