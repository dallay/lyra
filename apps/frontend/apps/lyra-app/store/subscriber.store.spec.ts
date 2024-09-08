import {afterEach, beforeEach, describe, expect, it, vi} from 'vitest';
import {createPinia, setActivePinia} from 'pinia';
import {useSubscriberStore} from '~/store/subscriber.store';
import {SubscriberId, SubscriberRequest} from '~/domain/subscriber';
import {OrganizationId} from '~/domain/organization';

const organizationId = OrganizationId.create('2fcaae02-4a82-4b88-b56c-281c8a3db22c');

const $api = {
  subscriber: {
    createSubscriber: vi.fn(),
    fetchAll: vi.fn().mockResolvedValue({ data: [], nextPageCursor: undefined }),
  },
};

const workspaceStore = {
  getCurrentOrganizationId: vi.fn().mockResolvedValue(organizationId),
};

describe('useSubscriberStore', () => {
  beforeEach(() => {
    vi.mock('#app', () => ({
      useNuxtApp: () => ({
        $api: $api,
      }),
    }));

    vi.mock('~/store/workspace.store', () => ({
      useWorkspaceStore: () => (workspaceStore),
    }));
    setActivePinia(createPinia());
  });

  afterEach(() => {
    vi.restoreAllMocks();
  });

  it('should fetch all subscribers', async () => {
    const store = useSubscriberStore();
    const response = await store.fetchAllSubscriber();
    expect(response.data).toEqual([]);
  });

  it('should create a new subscriber', async () => {
    const store = useSubscriberStore();
    const request: SubscriberRequest = { firstname: 'Test', lastname: 'Subscriber', email: 'test@example.com' };

    await store.createSubscriber(organizationId, request);
    expect($api.subscriber.createSubscriber).toHaveBeenCalledWith(organizationId, expect.any(SubscriberId), request);
  });

  it('should set subscriber filter options', () => {
    const store = useSubscriberStore();
    const options = { size: 50 };

    store.setSubscriberFilterOptions(options);

    expect(store.subscriberFilterOptions).toEqual({ size: 50 });
  });

  it('should set subscriber filter criteria', () => {
    const store = useSubscriberStore();
    const criteria = { column: 'name', operator: 'eq', values: ['John Doe'] };

    store.setSubscriberFilterCriteria(criteria);

    expect(store.subscriberFilterOptions.filterCriteria).toContainEqual({
      column: 'name',
      operator: 'eq',
      values: new Set(['John Doe']),
    });
  });

  it('should set subscriber search criteria', () => {
    const store = useSubscriberStore();
    const criteria = { column: 'email', operator: 'contains', values: ['example'] };

    store.setSubscriberFilterCriteria(criteria, true); // isSearch = true

    expect(store.subscriberFilterOptions.searchCriteria).toContainEqual({
      column: 'email',
      operator: 'contains',
      values: new Set(['example']),
    });
  });

  it('should set subscriber sort options', () => {
    const store = useSubscriberStore();
    const sort = { column: 'name', direction: 'asc' };

    store.setSubscriberFilterSort(sort);

    expect(store.subscriberFilterOptions.sortCriteria).toContainEqual(sort);
  });

  it('should handle errors during fetchAllSubscriber when current organization id fail', async () => {
    vi.mock('~/store/workspace.store', () => ({
      useWorkspaceStore: () => ({
        ...workspaceStore,
        getCurrentOrganizationId: vi.fn().mockRejectedValue(new Error('Failed to get organization ID')),
      }),
    }));
    const store = useSubscriberStore();
    const consoleErrorSpy = vi.spyOn(console, 'error').mockImplementation(() => {});

    const response = await store.fetchAllSubscriber();
    expect(response.data).toEqual([]);
    expect(consoleErrorSpy).toHaveBeenCalledWith('fetchAllSubscriber error:Error: Failed to get organization ID');
  });

  it('should handle errors during createSubscriber', async () => {
    const store = useSubscriberStore();
    const request: SubscriberRequest = { firstname: 'Test', lastname: 'Subscriber', email: 'test@example.com' };
    vi.mock('#app', () => ({
      useNuxtApp: () => ({
        $api: {
          subscriber: {
            createSubscriber: vi.fn().mockRejectedValue(new Error('Failed to create subscriber')),
            fetchAll: vi.fn().mockResolvedValue({ data: [], nextPageCursor: undefined }),
          },
        },
      }),
    }));

    const consoleErrorSpy = vi.spyOn(console, 'error').mockImplementation(() => {});

    await store.createSubscriber(organizationId, request);
    expect(consoleErrorSpy).toHaveBeenCalledWith('createSubscriber error:Error: Failed to create subscriber');
  });
});
