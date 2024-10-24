<template>
  <div class="bg-white dark:bg-gray-900 rounded-lg shadow-lg overflow-hidden mx-auto mt-4 w-full h-full">
    <div class="bg-gray-100 dark:bg-gray-800 p-2 flex items-center">
      <div class="flex space-x-2">
        <div class="w-3 h-3 rounded-full bg-red-500 dark:bg-red-700"></div>
        <div class="w-3 h-3 rounded-full bg-yellow-500 dark:bg-yellow-700"></div>
        <div class="w-3 h-3 rounded-full bg-green-500 dark:bg-green-700"></div>
      </div>
    </div>
    <div class="p-4 bg-white dark:bg-gray-900 dark:text-gray-100">
      <slot></slot>
    </div>
  </div>
</template>
