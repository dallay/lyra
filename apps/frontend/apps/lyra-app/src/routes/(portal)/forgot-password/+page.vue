<script lang="ts" setup>
import { reactive } from 'vue';
import { RouterLink } from 'vue-router';
import { XTextField, XButton, XLink } from '@lyra/ui';

import useStore from './store';
import useSchema from './schema';

const { state } = useStore();
const schema = useSchema();

const flux = reactive({
	flow: 1,

	send() {
		if (schema.validate()) {
			flux.flow = 2;
		}
	},

	code: '',
	inputCode() {
		if (flux.code.length === 6) flux.flow = 3;
	},
	resend() {
		// ...
	},
});
</script>

<template>
	<div class="w-full max-w-sm">
		<form
			v-if="flux.flow === 1"
			class="rounded bg-white px-8 pb-8 pt-6 shadow-md dark:bg-slate-800"
		>
			<div class="mb-8">
				<div class="mb-2 text-xl font-bold text-slate-900 dark:text-white">Forgot password?</div>
				<div>No worries, we'll send you reset instructions.</div>
			</div>

			<div class="mb-8">
				<XTextField
					v-model:value="state.form.email"
					label="Email"
					required
					:invalid="state.valdn.email"
				/>
			</div>

			<XButton class="mb-6 w-full" @click="flux.send">Send</XButton>

			<XLink to="/sign-in" class="flex justify-center">
				<div class="i-ic-round-arrow-back h-5 w-5"></div>
				<div>Back to sign in</div>
			</XLink>
		</form>

		<form
			v-if="flux.flow === 2"
			class="rounded bg-white px-8 pb-8 pt-6 shadow-md dark:bg-slate-800"
		>
			<div class="mb-8">
				<div class="mb-2 text-xl font-bold text-slate-900 dark:text-white">Check your email</div>
				<div>We have sent a verification code to your email.</div>
			</div>

			<div class="mb-8 space-y-2">
				<div>Enter the 6-digit verification code from your email:</div>

				<XTextField
					v-model:value="flux.code"
					class="text-center"
					maxlength="6"
					@input="flux.inputCode"
				/>
			</div>

			<div class="mb-4">
				Didn't receive the email?
				<div
					class="text-primary-500 hover:text-primary-600 dark:hover:text-primary-400 inline-flex cursor-pointer text-sm font-bold"
				>
					Click to resend
				</div>
			</div>
		</form>

		<form
			v-if="flux.flow === 3"
			class="rounded bg-white px-8 pb-8 pt-6 shadow-md dark:bg-slate-800"
		>
			<div class="i-lucide-check-circle mx-auto my-0 mb-6 h-12 w-12 text-green-500"></div>

			<div class="mb-4 text-center text-xl font-bold text-slate-900 dark:text-white">
				Password Reset
			</div>

			<div class="mb-6">
				Your password has been successfully reset. Click below to sign-in magically with your new
				password.
			</div>

			<div class="mb-8">
				<XTextField :value="'2803bn3VO7'" readonly>Your New Password:</XTextField>
			</div>

			<RouterLink
				class="text-primary-500 hover:text-primary-600 dark:hover:text-primary-400 flex space-x-1 text-sm font-bold"
				to="/sign-in"
				replace
			>
				<XButton class="w-full">Continue</XButton>
			</RouterLink>
		</form>
	</div>
</template>
