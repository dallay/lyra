import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import type {Form} from "@lyra/api-services";

export const usePostStore = defineStore("post", () => {
  const {$api} = useNuxtApp();
  const formList = ref<Form[]>([]);

  const fetchFormList = async () => {
    try {
      $api.form.fetchAll().then(data => {
        console.log(data)
      }).catch(error => {
        console.error(error)
      })
    } catch (error) {
      console.error("fetchPostList error:" + error);
    }
  };

  return {formList, fetchFormList};
});
