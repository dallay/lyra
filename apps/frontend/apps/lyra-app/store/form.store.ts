import {useNuxtApp} from "#app";
import {ref} from "vue";
import {defineStore} from "pinia";
import type {Form} from "@lyra/domain";

export const useFormStore = defineStore("forms", () => {
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
      console.error(`fetchFormList error:${error}`);
    }
  };

  return {formList, fetchFormList};
});
