import type FormRepository from '~/forms/domain/FormRepository.ts';
import type { Form } from '~/forms/domain/Form.ts';
import {
	buildParams,
	type CriteriaParam,
	type PageResponse,
	type QuerySort,
} from '~/types/types.ts';
import request from '@/request/request.ts';
import { injectable } from 'inversify';
import type FormDestroyerRepository from '~/forms/domain/FormDestroyerRepository.ts';
import type FormFinderRepository from '~/forms/domain/FormFinderRepository.ts';

@injectable()
export default class ApiFormRepository
	implements FormRepository, FormFinderRepository, FormDestroyerRepository
{
	private headers: HeadersInit = {
		Accept: 'application/vnd.api.v1+json',
	};
	async find(id: string): Promise<Form> {
		const response = await request<Form>(`/forms/${id}`, {
			method: 'GET',
			headers: this.headers,
		});
		if (!response || !response.ok) {
			throw new Error('Error fetching form');
		}
		return response._data as Form;
	}

	async search(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size?: number,
		cursor?: string
	): Promise<PageResponse<Form>> {
		const params = buildParams(criteria, sort, size, cursor);
		const response = await request<PageResponse<Form>>('/forms', {
			method: 'GET',
			headers: this.headers,
			params: params,
		});
		if (!response || !response.ok) {
			throw new Error('Error fetching forms');
		}
		return {
			data: response._data?.data || [],
			nextPageCursor: response._data?.nextPageCursor || '',
		};
	}

	async update(form: Form): Promise<void> {
		const response = await request<Form>(`/forms/update/${form.id}`, {
			method: 'PUT',
			headers: this.headers,
			body: JSON.stringify(form),
		});
		if (!response || !response.ok) {
			throw new Error('Error updating form');
		}
	}

	async delete(id: string): Promise<void> {
		const response = await request(`/forms/${id}`, {
			method: 'DELETE',
			headers: this.headers,
		});
		if (!response || !response.ok) {
			throw new Error('Error deleting form');
		}
	}

	async create(form: Form): Promise<void> {
		const response = await request<Form>(`/forms/${form.id}`, {
			method: 'PUT',
			headers: this.headers,
			body: JSON.stringify(form),
		});
		if (!response || !response.ok) {
			throw new Error('Error creating form');
		}
	}
}
