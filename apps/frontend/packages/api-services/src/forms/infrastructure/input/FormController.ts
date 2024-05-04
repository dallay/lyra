import FindFormQueryHandler, {
	FIND_FORM_QUERY_HANDLER_PROVIDER,
} from '~/forms/application/find/FindFormQueryHandler.ts';
import { inject, injectable } from 'inversify';
import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import SearchFormsQueryHandler, {
	SEARCH_FORMS_QUERY_HANDLER_PROVIDER,
} from '~/forms/application/search/SearchFormsQueryHandler.ts';
import DeleteFormCommandHandler, {
	DELETE_FORM_COMMAND_HANDLER_PROVIDER,
} from '~/forms/application/delete/DeleteFormCommandHandler.ts';
import FormResponse from '~/forms/application/FormResponse.ts';
import UpdateFormCommandHandler, {
	UPDATE_FORM_COMMAND_HANDLER_PROVIDER,
} from '~/forms/application/update/UpdateFormCommandHandler.ts';
import UpdateFormRequest from '~/forms/infrastructure/input/request/UpdateFromRequest.ts';

export const FORM_CONTROLLER_PROVIDER = 'FORM_CONTROLLER_PROVIDER';

@injectable()
export default class FormController {
	constructor(
		@inject(FIND_FORM_QUERY_HANDLER_PROVIDER) private findFormQueryHandler: FindFormQueryHandler,
		@inject(SEARCH_FORMS_QUERY_HANDLER_PROVIDER)
		private searchFormsQueryHandler: SearchFormsQueryHandler,
		@inject(UPDATE_FORM_COMMAND_HANDLER_PROVIDER)
		private updateFormCommandHandler: UpdateFormCommandHandler,
		@inject(DELETE_FORM_COMMAND_HANDLER_PROVIDER)
		private deleteFormCommandHandler: DeleteFormCommandHandler
	) {}

	async getForms(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size: number = 10,
		cursor: string = ''
	): Promise<PageResponse<FormResponse>> {
		return this.searchFormsQueryHandler.handle({ criteria, sort, size, cursor });
	}

	async findForm(id: string) {
		return this.findFormQueryHandler.handle({ id });
	}

	async updateForm(id: string, request: UpdateFormRequest) {
		return this.updateFormCommandHandler.handle({
			id: id,
			name: request.name,
			header: request.header,
			description: request.description,
			inputPlaceholder: request.inputPlaceholder,
			buttonText: request.buttonText,
			buttonColor: request.buttonColor,
			backgroundColor: request.backgroundColor,
			textColor: request.textColor,
			buttonTextColor: request.buttonTextColor,
		});
	}

	async deleteForm(id: string) {
		return this.deleteFormCommandHandler.handle({ id });
	}
}
