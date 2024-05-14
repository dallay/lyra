import type FormRepository from '~/forms/domain/FormRepository.ts';
import type Form from '~/forms/domain/Form.ts';
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
import type FormResponse from '~/forms/domain/FormResponse.ts';
import type { FetchOptions, FetchRequest, FetchResponse } from 'ofetch';

@injectable()
export default class ApiFormRepository
	implements FormRepository, FormFinderRepository, FormDestroyerRepository
{
  constructor(
    private readonly httpRequest: <T>(
      request: FetchRequest,
      options?: FetchOptions,
    ) => Promise<FetchResponse<T>> = request,
    private readonly headers: HeadersInit = {
      Accept: 'application/vnd.api.v1+json',
    },
  ) {
  }

	async find(id: string): Promise<FormResponse> {
    const response = await this.httpRequest<FormResponse>(`/forms/${id}`, {
			method: 'GET',
			headers: this.headers,
		});
		if (!response || !response.ok) {
      throw new Error('Error fetching form', { cause: 'Couldn\'t find form' });
		}
		return response._data || ({} as FormResponse);
	}

	async search(
		criteria?: CriteriaParam,
		sort?: QuerySort,
		size?: number,
		cursor?: string
	): Promise<PageResponse<FormResponse>> {
		const params = buildParams(criteria, sort, size, cursor);
    const response = await this.httpRequest<PageResponse<FormResponse>>('/forms', {
			method: 'GET',
			headers: this.headers,
			params: params,
		});
		if (!response || !response.ok) {
      throw new Error('Error fetching forms', { cause: 'Couldn\'t search forms' });
		}
		return {
			data: response._data?.data || [],
			nextPageCursor: response._data?.nextPageCursor || '',
		};
	}

	async update(form: Form): Promise<void> {
    const response = await this.httpRequest<FormResponse>(`/forms/update/${form.id.value}`, {
			method: 'PUT',
			headers: this.headers,
			body: JSON.stringify(form.toPrimitives()),
		});
		if (!response || !response.ok) {
      throw new Error('Error updating form', { cause: 'Couldn\'t update form' });
		}
	}

	async delete(id: string): Promise<void> {
    const response = await this.httpRequest(`/forms/${id}`, {
			method: 'DELETE',
			headers: this.headers,
		});
		if (!response || !response.ok) {
      throw new Error('Error deleting form', { cause: 'Couldn\'t delete form' });
		}
	}

	async create(form: Form): Promise<void> {
    const response = await this.httpRequest<FormResponse>(`/forms/${form.id.value}`, {
			method: 'PUT',
			headers: this.headers,
			body: JSON.stringify(form.toPrimitives()),
		});
		if (!response || !response.ok) {
      throw new Error('Error creating form', { cause: 'Couldn\'t create form' });
		}
	}
}
