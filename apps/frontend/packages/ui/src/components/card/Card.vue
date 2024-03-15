<template>
	<div class="Card">
		<div class="Card-Header">
			<slot name="header"></slot>
		</div>

		<div class="Card-Body">
			<div class="Card-Title">
				<slot name="title"></slot>
			</div>

			<div class="Card-Subtitle">
				<slot name="subtitle"></slot>
			</div>

			<div class="CardContent">
				<slot></slot>
			</div>

			<div class="Card-Footer">
				<slot name="footer"></slot>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.Card {
	@apply rounded-lg border border-gray-200 bg-white shadow dark:border-gray-700 dark:bg-gray-800;
}

.Card-Header {
	@apply empty:hidden;
}

.Card-Body {
	@apply flex flex-col p-4 lg:p-6;
}

.Card-Title {
	@apply mb-2 text-2xl font-semibold empty:hidden;
}

.Card-Subtitle {
	@apply mb-2 text-lg text-zinc-400 empty:hidden dark:text-zinc-500;
}

.Card-Footer {
	@apply mt-8 empty:hidden;
}
</style>
