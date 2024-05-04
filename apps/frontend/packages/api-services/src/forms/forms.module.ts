import { ContainerModule, type interfaces } from 'inversify';
import type FormRepository from '~/forms/domain/FormRepository.ts';
import { FORM_REPOSITORY_PROVIDER } from '~/forms/domain/FormRepository.ts';
import FetchFormRepository from '~/forms/infrastructure/output/FetchFormRepository.ts';
import type FormResponse from './application/FormResponse';
import SearchFormsQueryHandler, {
	SEARCH_FORMS_QUERY_HANDLER_PROVIDER,
} from './application/search/SearchFormsQueryHandler';
import type SearchFormsQuery from './application/search/SearchFormsQuery';
import FormsSearcher, { FORM_SEARCHER_PROVIDER } from './application/search/FormsSearcher';
import FormFinder, { FORM_FINDER_PROVIDER } from './application/find/FormFinder';
import type FindFormQuery from './application/find/FindFormQuery';
import FindFormQueryHandler, {
	FIND_FORM_QUERY_HANDLER_PROVIDER,
} from './application/find/FindFormQueryHandler';
import FormController, {
	FORM_CONTROLLER_PROVIDER,
} from '~/forms/infrastructure/input/FormController.ts';
import FormUpdater, { FORM_UPDATER_PROVIDER } from '~/forms/application/update/FormUpdater.ts';
import UpdateFormCommandHandler, {
	UPDATE_FORM_COMMAND_HANDLER_PROVIDER,
} from '~/forms/application/update/UpdateFormCommandHandler.ts';
import FormDestroyer, {
	FORM_DESTROYER_PROVIDER,
} from '~/forms/application/delete/FormDestroyer.ts';
import DeleteFormCommandHandler, {
	DELETE_FORM_COMMAND_HANDLER_PROVIDER,
} from '~/forms/application/delete/DeleteFormCommandHandler.ts';
import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import { FORM_DESTROYER_REPOSITORY_PROVIDER } from '~/forms/domain/FormDestroyerRepository.ts';

export const formsModule = new ContainerModule((bind: interfaces.Bind) => {
	bind<FormRepository>(FORM_REPOSITORY_PROVIDER).to(FetchFormRepository).inSingletonScope();
	bind<FormDestroyerRepository>(FORM_DESTROYER_REPOSITORY_PROVIDER)
		.to(FetchFormRepository)
		.inSingletonScope();
	bind<SearchFormsQueryHandler>(SEARCH_FORMS_QUERY_HANDLER_PROVIDER).to(SearchFormsQueryHandler);
	bind<FormsSearcher>(FORM_SEARCHER_PROVIDER).to(FormsSearcher);
	bind<FindFormQueryHandler>(FIND_FORM_QUERY_HANDLER_PROVIDER).to(FindFormQueryHandler);
	bind<FormFinder>(FORM_FINDER_PROVIDER).to(FormFinder);
	bind<FormUpdater>(FORM_UPDATER_PROVIDER).to(FormUpdater);
	bind<UpdateFormCommandHandler>(UPDATE_FORM_COMMAND_HANDLER_PROVIDER).to(UpdateFormCommandHandler);
	bind<FormDestroyer>(FORM_DESTROYER_PROVIDER).to(FormDestroyer);
	bind<DeleteFormCommandHandler>(DELETE_FORM_COMMAND_HANDLER_PROVIDER).to(DeleteFormCommandHandler);
	bind<FormController>(FORM_CONTROLLER_PROVIDER).to(FormController);
});
export {
	SEARCH_FORMS_QUERY_HANDLER_PROVIDER,
	FIND_FORM_QUERY_HANDLER_PROVIDER,
	FORM_REPOSITORY_PROVIDER,
	FORM_SEARCHER_PROVIDER,
	FORM_FINDER_PROVIDER,
	FORM_UPDATER_PROVIDER,
	UPDATE_FORM_COMMAND_HANDLER_PROVIDER,
	FORM_DESTROYER_PROVIDER,
	DELETE_FORM_COMMAND_HANDLER_PROVIDER,
	FORM_DESTROYER_REPOSITORY_PROVIDER,
	FORM_CONTROLLER_PROVIDER,
};

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
	FormController,
};
export { FetchFormRepository };
