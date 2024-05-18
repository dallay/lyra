import 'reflect-metadata';
import { type MockProxy, mock } from 'vitest-mock-extended';
import { describe, beforeEach, it, expect } from 'vitest';
import FormsSearcher from '@/forms/application/search/FormsSearcher.ts';
import Form from '@/forms/domain/Form.ts';
import type FormFinderRepository from '@/forms/domain/FormFinderRepository.ts';
import FormResponse from '@/forms/domain/FormResponse.ts';
import type { CriteriaParam, PageResponse, QuerySort } from '@/types/types.ts';

describe('FormsSearcher', () => {
	let formsSearcher: FormsSearcher;
	let formRepository: MockProxy<FormFinderRepository>;
	let form: Form;
	let formResponse: FormResponse;
	let pageResponse: PageResponse<FormResponse>;

	beforeEach(() => {
		formRepository = mock<FormFinderRepository>();
		formsSearcher = new FormsSearcher(formRepository);
		form = Form.fromPrimitives({
			id: crypto.randomUUID(),
			name: 'Form',
			header: 'Form header',
			description: 'Form description',
			textColor: '#000000',
			buttonColor: '#000000',
			buttonText: 'Submit',
			inputPlaceholder: 'Input placeholder',
			backgroundColor: '#FFFFFF',
			buttonTextColor: '#FFFFFF',
		});
		formResponse = FormResponse.from(form);
		pageResponse = {
			data: [formResponse],
			nextPageCursor: 'cursor',
		};
	});

	it('searches forms successfully', async () => {
		const searchQuery = {
			criteria: {} as CriteriaParam,
			size: 10,
			cursor: 'cursor',
			sort: {} as QuerySort,
		};

		formRepository.search.mockResolvedValue(pageResponse);

		const result = await formsSearcher.search(searchQuery);

		expect(result).toEqual(pageResponse);
		expect(formRepository.search).toHaveBeenCalledWith(
			searchQuery.criteria,
			searchQuery.sort,
			searchQuery.size,
			searchQuery.cursor
		);
	});

	it('returns empty page when no forms are found', async () => {
		const searchQuery = {
			criteria: {} as CriteriaParam,
			size: 10,
			cursor: 'cursor',
			sort: {} as QuerySort,
		};

		const pageResponse: PageResponse<FormResponse> = { data: [], nextPageCursor: undefined };
		formRepository.search.mockReturnValue(Promise.resolve(pageResponse));

		const result = await formsSearcher.search(searchQuery);

		expect(result).toEqual({ data: [], nextPageCursor: undefined });
		expect(formRepository.search).toHaveBeenCalledWith(
			searchQuery.criteria,
			searchQuery.sort,
			searchQuery.size,
			searchQuery.cursor
		);
	});
});
