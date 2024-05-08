import type { CriteriaParam, PageResponse, QuerySort } from '~/types/types.ts';
import type FormResponse from '~/forms/domain/FormResponse.ts';

export default interface FormFinderRepository {
  search(
    criteria?: CriteriaParam,
    sort?: QuerySort,
    size?: number,
    cursor?: string,
  ): Promise<PageResponse<FormResponse>>;

  find(id: string): Promise<FormResponse>;
}
