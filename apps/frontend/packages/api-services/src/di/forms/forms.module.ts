import { ContainerModule, type interfaces } from 'inversify';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import ApiFormRepository from '~/forms/infrastructure/output/ApiFormRepository.ts';

import FormController from '~/forms/infrastructure/input/FormController.ts';
import FormUpdater from '~/forms/application/update/FormUpdater.ts';
import UpdateFormCommandHandler from '~/forms/application/update/UpdateFormCommandHandler.ts';
import FormDestroyer from '~/forms/application/delete/FormDestroyer.ts';
import DeleteFormCommandHandler from '~/forms/application/delete/DeleteFormCommandHandler.ts';
import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';
import FormCreator from '~/forms/application/create/FormCreator.ts';
import CreateFormCommandHandler from '~/forms/application/create/CreateFormCommandHandler.ts';
import SearchFormsQueryHandler from '~/forms/application/search/SearchFormsQueryHandler';
import {
	CREATE_FORM_COMMAND_HANDLER_PROVIDER,
	DELETE_FORM_COMMAND_HANDLER_PROVIDER,
	FIND_FORM_QUERY_HANDLER_PROVIDER,
	FORM_CONTROLLER_PROVIDER,
	FORM_CREATOR_PROVIDER,
	FORM_DESTROYER_PROVIDER,
	FORM_DESTROYER_REPOSITORY_PROVIDER,
	FORM_FINDER_PROVIDER,
	FORM_FINDER_REPOSITORY_PROVIDER,
	FORM_REPOSITORY_PROVIDER,
	FORM_SEARCHER_PROVIDER,
	FORM_UPDATER_PROVIDER,
	SEARCH_FORMS_QUERY_HANDLER_PROVIDER,
	UPDATE_FORM_COMMAND_HANDLER_PROVIDER,
} from '~/di/forms/forms.module.types.ts';
import FormsSearcher from '~/forms/application/search/FormsSearcher';
import FindFormQueryHandler from '~/forms/application/find/FindFormQueryHandler';
import FormFinder from '~/forms/application/find/FormFinder';
import type FormResponse from '~/forms/application/FormResponse';
import type SearchFormsQuery from '~/forms/application/search/SearchFormsQuery';
import type FindFormQuery from '~/forms/application/find/FindFormQuery';

export const formsModule = new ContainerModule((bind: interfaces.Bind) => {
	bind<FormRepository>(FORM_REPOSITORY_PROVIDER).to(ApiFormRepository).inSingletonScope();
	bind<FormFinderRepository>(FORM_FINDER_REPOSITORY_PROVIDER)
		.to(ApiFormRepository)
		.inSingletonScope();
	bind<FormDestroyerRepository>(FORM_DESTROYER_REPOSITORY_PROVIDER)
		.to(ApiFormRepository)
		.inSingletonScope();
	bind<SearchFormsQueryHandler>(SEARCH_FORMS_QUERY_HANDLER_PROVIDER).to(SearchFormsQueryHandler);
	bind<FormsSearcher>(FORM_SEARCHER_PROVIDER).to(FormsSearcher);
	bind<FindFormQueryHandler>(FIND_FORM_QUERY_HANDLER_PROVIDER).to(FindFormQueryHandler);
	bind<FormFinder>(FORM_FINDER_PROVIDER).to(FormFinder);
	bind<FormUpdater>(FORM_UPDATER_PROVIDER).to(FormUpdater);
	bind<UpdateFormCommandHandler>(UPDATE_FORM_COMMAND_HANDLER_PROVIDER).to(UpdateFormCommandHandler);
	bind<FormDestroyer>(FORM_DESTROYER_PROVIDER).to(FormDestroyer);
	bind<DeleteFormCommandHandler>(DELETE_FORM_COMMAND_HANDLER_PROVIDER).to(DeleteFormCommandHandler);
	bind<FormCreator>(FORM_CREATOR_PROVIDER).to(FormCreator);
	bind<CreateFormCommandHandler>(CREATE_FORM_COMMAND_HANDLER_PROVIDER).to(CreateFormCommandHandler);
	bind<FormController>(FORM_CONTROLLER_PROVIDER).to(FormController);
});

export type {
	FormRepository,
	FormResponse,
	SearchFormsQueryHandler,
	SearchFormsQuery,
	FormsSearcher,
	FormFinder,
	FormDestroyerRepository,
	FindFormQueryHandler,
	FindFormQuery,
	FormUpdater,
	UpdateFormCommandHandler,
	FormDestroyer,
	DeleteFormCommandHandler,
	FormFinderRepository,
	FormController,
	FormCreator,
	CreateFormCommandHandler,
};
export { ApiFormRepository };
