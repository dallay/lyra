import { ContainerModule, type interfaces } from 'inversify';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import ApiFormRepository from '~/forms/infrastructure/output/ApiFormRepository.ts';

import FormController from '~/forms/infrastructure/input/FormController.ts';
import FormUpdater from '~/forms/application/update/FormUpdater.ts';
import FormDestroyer from '~/forms/application/delete/FormDestroyer.ts';
import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import FormCreator from '~/forms/application/create/FormCreator.ts';
import {
	FORM_CONTROLLER_PROVIDER,
	FORM_CREATOR_PROVIDER,
	FORM_DESTROYER_PROVIDER,
	FORM_DESTROYER_REPOSITORY_PROVIDER,
	FORM_FINDER_PROVIDER,
	FORM_FINDER_REPOSITORY_PROVIDER,
	FORM_REPOSITORY_PROVIDER,
	FORM_SEARCHER_PROVIDER,
	FORM_UPDATER_PROVIDER,
} from '~/di/forms/forms.module.types.ts';
import FormsSearcher from '~/forms/application/search/FormsSearcher';
import FormFinder from '~/forms/application/find/FormFinder';
import FormId from '~/forms/domain/FormId';
import Form from '~/forms/domain/Form';
import FormResponse from '~/forms/domain/FormResponse';
import CreateFormRequest from '~/forms/infrastructure/input/request/CreateFormRequest';
import UpdateFormRequest from '~/forms/infrastructure/input/request/UpdateFormRequest';
import HexColor from '~/forms/domain/HexColor';
import {REQUEST_SERVICE, type RequestService} from "~/request/RequestService.ts";
import {HttpRequestService} from "~/request/HttpRequestService.ts";

export const createFormsModule = (apiUrl:string) => new ContainerModule((bind: interfaces.Bind) => {
  bind<RequestService>(REQUEST_SERVICE).toDynamicValue((_) => {
    return new HttpRequestService(apiUrl);
  }).inSingletonScope();
	bind<FormRepository>(FORM_REPOSITORY_PROVIDER).toDynamicValue((context) => {
    const requestService = context.container.get<RequestService>(REQUEST_SERVICE);
    return new ApiFormRepository(requestService);
  }).inSingletonScope();
  bind<FormFinderRepository>(FORM_FINDER_REPOSITORY_PROVIDER).toDynamicValue((context) => {
    const requestService = context.container.get<RequestService>(REQUEST_SERVICE);
    return new ApiFormRepository(requestService);
  }).inSingletonScope();
  bind<FormDestroyerRepository>(FORM_DESTROYER_REPOSITORY_PROVIDER).toDynamicValue((context) => {
    const requestService = context.container.get<RequestService>(REQUEST_SERVICE);
    return new ApiFormRepository(requestService);
  }).inSingletonScope();
	bind<FormsSearcher>(FORM_SEARCHER_PROVIDER).to(FormsSearcher);
	bind<FormFinder>(FORM_FINDER_PROVIDER).to(FormFinder);
	bind<FormUpdater>(FORM_UPDATER_PROVIDER).to(FormUpdater);
	bind<FormDestroyer>(FORM_DESTROYER_PROVIDER).to(FormDestroyer);
	bind<FormCreator>(FORM_CREATOR_PROVIDER).to(FormCreator);
	bind<FormController>(FORM_CONTROLLER_PROVIDER).to(FormController);
});
export type { FormRepository, FormDestroyerRepository, FormFinderRepository };
export {
	Form,
	FormsSearcher,
	FormFinder,
	FormUpdater,
	FormDestroyer,
	FormController,
	FormCreator,
	CreateFormRequest,
	UpdateFormRequest,
  ApiFormRepository,
  FormId,
  HexColor,
  FormResponse,
};
