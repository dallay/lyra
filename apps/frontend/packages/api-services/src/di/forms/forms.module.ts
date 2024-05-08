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
import type Form from '~/forms/domain/Form';
import FormResponse from '~/forms/domain/FormResponse';
import type CreateFormRequest from '~/forms/infrastructure/input/request/CreateFormRequest';
import type UpdateFormRequest from '~/forms/infrastructure/input/request/UpdateFormRequest';
import HexColor from '~/forms/domain/HexColor';

export const formsModule = new ContainerModule((bind: interfaces.Bind) => {
	bind<FormRepository>(FORM_REPOSITORY_PROVIDER).to(ApiFormRepository).inSingletonScope();
	bind<FormFinderRepository>(FORM_FINDER_REPOSITORY_PROVIDER)
		.to(ApiFormRepository)
		.inSingletonScope();
	bind<FormDestroyerRepository>(FORM_DESTROYER_REPOSITORY_PROVIDER)
		.to(ApiFormRepository)
		.inSingletonScope();
	bind<FormsSearcher>(FORM_SEARCHER_PROVIDER).to(FormsSearcher);
	bind<FormFinder>(FORM_FINDER_PROVIDER).to(FormFinder);
	bind<FormUpdater>(FORM_UPDATER_PROVIDER).to(FormUpdater);
	bind<FormDestroyer>(FORM_DESTROYER_PROVIDER).to(FormDestroyer);
	bind<FormCreator>(FORM_CREATOR_PROVIDER).to(FormCreator);
	bind<FormController>(FORM_CONTROLLER_PROVIDER).to(FormController);
});

export type {
	Form,
	FormRepository,
	FormsSearcher,
	FormFinder,
	FormDestroyerRepository,
	FormUpdater,
	FormDestroyer,
	FormFinderRepository,
	FormController,
	FormCreator,
	CreateFormRequest,
	UpdateFormRequest,
};

export { ApiFormRepository, FormId, HexColor, FormResponse };
