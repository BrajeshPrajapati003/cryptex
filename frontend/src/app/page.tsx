const services = [
  {
    name: "Authentication",
    description:
      "JWT authentication, refresh tokens, verification, password reset, and user management.",
  },
  {
    name: "Notifications",
    description:
      "Dedicated notification service with email providers, templates, persistence, and tests.",
  },
  {
    name: "API Gateway",
    description:
      "A single entry point for routing client requests to backend services.",
  },
];

const stack = [
  "Java 21",
  "Spring Boot",
  "Spring Security",
  "PostgreSQL",
  "Spring Cloud Gateway",
  "Next.js",
  "TypeScript",
];

export default function Home() {
  return (
    <main className="min-h-screen bg-zinc-950 text-zinc-100">
      <section className="mx-auto flex min-h-screen max-w-6xl flex-col justify-center px-6 py-20 sm:px-10">
        <div className="max-w-4xl">
          <p className="mb-5 text-sm font-medium uppercase tracking-[0.3em] text-cyan-400">
            CRYPTEX
          </p>

          <h1 className="text-4xl font-semibold tracking-tight sm:text-6xl">
            Building the backend foundation for a cryptocurrency intelligence
            platform.
          </h1>

          <p className="mt-6 max-w-2xl text-lg leading-8 text-zinc-400">
            A Java and Spring Boot project exploring authentication, service
            boundaries, notifications, API gateways, persistence, testing, and
            the architecture required to grow a larger platform.
          </p>

          <div className="mt-8 flex flex-wrap gap-3">
            {stack.map((item) => (
              <span
                key={item}
                className="rounded-full border border-zinc-800 bg-zinc-900 px-4 py-2 text-sm text-zinc-300"
              >
                {item}
              </span>
            ))}
          </div>
        </div>

        <div className="mt-20 grid gap-5 md:grid-cols-3">
          {services.map((service) => (
            <article
              key={service.name}
              className="rounded-2xl border border-zinc-800 bg-zinc-900/70 p-6"
            >
              <h2 className="text-xl font-semibold">{service.name}</h2>
              <p className="mt-3 text-sm leading-6 text-zinc-400">
                {service.description}
              </p>
            </article>
          ))}
        </div>

        <div className="mt-12 rounded-2xl border border-cyan-900/50 bg-cyan-950/20 p-6">
          <p className="text-sm font-semibold text-cyan-300">
            Development status
          </p>
          <p className="mt-2 text-sm leading-6 text-zinc-400">
            The backend is currently the primary focus. Cryptocurrency data,
            portfolio workflows, AI insights, and the complete product UI are
            being developed incrementally.
          </p>
        </div>
      </section>
    </main>
  );
}
