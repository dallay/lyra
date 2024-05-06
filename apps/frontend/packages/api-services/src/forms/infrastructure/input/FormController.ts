import { inject, injectable } from 'inversify';
import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import FormResponse from '~/forms/application/FormResponse.ts';
import CreateFormRequest from '~/forms/infrastructure/input/request/CreateFormRequest.ts';
import UpdateFormRequest from '~/forms/infrastructure/input/request/UpdateFormRequest.ts';
import {
	FORM_CREATOR_PROVIDER,
	FORM_DESTROYER_PROVIDER,
	FORM_FINDER_PROVIDER,
	FORM_SEARCHER_PROVIDER,
	FORM_UPDATER_PROVIDER,
} from '~/di/forms/forms.module.types.ts';
import type FormFinder from '~/forms/application/find/FormFinder.ts';
import type FormsSearcher from '~/forms/application/search/FormsSearcher.ts';
import type FormUpdater from '~/forms/application/update/FormUpdater.ts';
import type FormDestroyer from '~/forms/application/delete/FormDestroyer.ts';
import type FormCreator from '~/forms/application/create/FormCreator.ts';
import type { Form } from '~/forms/domain/Form.ts';

@injectable()
export default class FormController {
	constructor(
		@inject(FORM_FINDER_PROVIDER) private formFinder: FormFinder,
		@inject(FORM_SEARCHER_PROVIDER)
		private formsSearcher: FormsSearcher,
		@inject(FORM_UPDATER_PROVIDER)
		private formUpdater: FormUpdater,
		@inject(FORM_DESTROYER_PROVIDER)
		private formDestroyer: FormDestroyer,
		@inject(FORM_CREATOR_PROVIDER)
		private formCreator: FormCreator
	) {}

	async create(request: CreateFormRequest): Promise<void> {
		await this.formCreator.create({
			id: request.id,
			name: request.name,
			header: request.header,
			description: request.description,
			inputPlaceholder: request.inputPlaceholder,
			buttonText: request.buttonText,
			buttonColor: request.buttonColor,
			backgroundColor: request.backgroundColor,
			textColor: request.textColor,
			buttonTextColor: request.buttonTextColor,
		} as Form);
	}

	async findAll(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size: number = 10,
		cursor: string = ''
	): Promise<PageResponse<FormResponse>> {
		return this.formsSearcher.search({ criteria, sort, size, cursor });
	}

	async find(id: string) {
		return this.formFinder.find(id);
	}

	async update(id: string, request: UpdateFormRequest) {
		return this.formUpdater.update({
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
		} as Form);
	}

	async delete(id: string) {
		return this.formDestroyer.destroy(id);
	}
}
