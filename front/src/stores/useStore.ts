import create from 'zustand';

interface StoreState {
  projects: { name: string; properties: number }[];
  addProject: (project: { name: string; properties: number }) => void;
}

export const useStore = create<StoreState>((set) => ({
  projects: [],
  addProject: (project) => set((state) => ({ projects: [...state.projects, project] })),
}));